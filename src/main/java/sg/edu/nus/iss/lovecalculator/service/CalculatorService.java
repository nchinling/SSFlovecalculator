package sg.edu.nus.iss.lovecalculator.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.iss.lovecalculator.model.Calculator;
import sg.edu.nus.iss.lovecalculator.repo.CalculatorRepo;

@Service
public class CalculatorService {
    @Autowired
    private CalculatorRepo calculatorrepo;
    
    @Value("${workshop.love.calculator.url}")
    private String loveCalculatorUrl;

    @Value("${workshop.love.calculator.api.key}")
    private String loveCalculatorKey;

    @Value("${workshop.love.calculator.api.host}")
    private String loveCalculatorHost;

    public void save(final Calculator calculator){
        calculatorrepo.save(calculator);
    }

 
    public Optional<Calculator> findById(final String dataId) throws IOException{
        return calculatorrepo.findById(dataId);
    }  

    public List<Calculator> findAll(int startIndex) throws IOException{
        return calculatorrepo.findAll(startIndex);
    }

    public Optional<Calculator> getResult(String fname, String sname)
        throws IOException{
        System.out.println("loveCalculatorUrl: " + loveCalculatorUrl);
        System.out.println("loveCalculatorKey: " + loveCalculatorKey);
        
        String loveUrl = UriComponentsBuilder
                              .fromUriString(loveCalculatorUrl)
                              .queryParam("fname", 
                                    fname.replaceAll(" ", "+"))
                              .queryParam("sname", 
                                    sname.replaceAll(" ", "+"))
                              .toUriString();
                              
        HttpHeaders headers = new HttpHeaders();
        //need to use env variable. will be penalised if not.
        headers.set("X-RapidAPI-Key", loveCalculatorKey);
        headers.set("X-RapidAPI-Host", loveCalculatorHost);

        //GET request. GET request doesn't have body. Instead information is sent 
        //via headers and query params (URI)
        RequestEntity req = RequestEntity.get(loveUrl)
                            .headers(headers)
                            .build();

        // RequestEntity<String> request = RequestEntity
        // .post(URI.create("https://example.com/api/user"))
        // .headers(headers)
        // .body(requestBody);

        RestTemplate template= new RestTemplate();
        ResponseEntity<String> r  = template.exchange(req, 
                String.class);

        //r.getBody() returns a string. a json string in this case. 
        Calculator c = Calculator.createUserObject(r.getBody());
        
        //if not null, save to redisobject.
        if(c == null)
            return Optional.empty();
        return Optional.of(c);
    }

}


