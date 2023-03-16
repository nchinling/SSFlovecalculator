package sg.edu.nus.iss.lovecalculator.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.iss.lovecalculator.model.Calculator;

@Service
public class CalculatorService {
    
    @Value("${workshop.love.calculator.url}")
    private String loveCalculatorUrl;

    @Value("${workshop.love.calculator.api.key}")
    private String loveCalculatorKey;

    public Optional<Calculator> getResult(String fname, String sname)
        throws IOException{
        System.out.println("loveCalculatorUrl: " + loveCalculatorUrl);
        System.out.println("loveCalculatorKey: " + loveCalculatorKey);
        
        String url = "https://love-calculator.p.rapidapi.com/getPercentage?fname=Chin&sname=Ling";
        String loveUrl = UriComponentsBuilder
                              .fromUriString(loveCalculatorUrl)
                              .queryParam("fname", 
                                    fname.replaceAll(" ", "+"))
                              .queryParam("sname", 
                                    sname.replaceAll(" ", "+"))
                              .toUriString();
                              
        HttpHeaders headers = new HttpHeaders();
        //need to use env variable. will be penalised if not.
        headers.set("X-RapidAPI-Key", "7012b59ca2msh3f3a6f36ec7ce3bp1d5f4cjsnf4bdf61c60ed");
        headers.set("X-RapidAPI-Host", "love-calculator.p.rapidapi.com");

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
        Calculator c = Calculator.create(r.getBody());
        
        //if not null, save to redisobject.
        if(c == null)
            return Optional.empty();
        return Optional.of(c);
    }


}


