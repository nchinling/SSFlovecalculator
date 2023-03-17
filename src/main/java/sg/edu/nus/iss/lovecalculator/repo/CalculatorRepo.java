package sg.edu.nus.iss.lovecalculator.repo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.lovecalculator.model.Calculator;

@Repository
public class CalculatorRepo {
    
      //autowired in a bean.
      @Autowired @Qualifier("lovescore")

      private RedisTemplate<String, String> template;

      private static final String DATA_LIST = "datalist";

    public void save(Calculator calc){
        this.template.opsForValue().set(calc.getDataId(), calc.toJSON().toString());
    }


    public Optional<Calculator> findById(String dataId) throws IOException{
        String json = template.opsForValue().get(dataId);
        if(null == json|| json.trim().length() <= 0){
            return Optional.empty();
        }

        return Optional.of(Calculator.createUserObject(json));
    }


    public List<Calculator> findAll(int startIndex) throws IOException{
        Set<String> allKeys = template.keys("*");
        List<Calculator> calcarray = new LinkedList<>();
        for (String key : allKeys) {
            String result = template.opsForValue().get(key);

            calcarray.add(Calculator.createUserObjectFromRedis(result));
        }

        return calcarray;

    }

}
