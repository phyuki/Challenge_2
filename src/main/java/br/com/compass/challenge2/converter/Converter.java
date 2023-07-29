package br.com.compass.challenge2.converter;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    private static ModelMapper modelMapper;
    public Converter() {
        this.modelMapper = new ModelMapper();
        // Configurar o ModelMapper para usar a estratégia padrão (deixar campos nulos se não houver correspondência)
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        // Configurar o ModelMapper para não criar instâncias durante o mapeamento
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
    }
    public static <T> T convert(Object dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }
}

