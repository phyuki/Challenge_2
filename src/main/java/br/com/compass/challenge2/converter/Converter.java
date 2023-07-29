package br.com.compass.challenge2.converter;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    private static ModelMapper modelMapper;
    
    static {
        modelMapper = new ModelMapper();
        // Configurar o ModelMapper para usar a estratégia padrão (deixar campos nulos se não houver correspondência)
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        // Configurar o ModelMapper para não criar instâncias durante o mapeamento
        modelMapper.getConfiguration().setSkipNullEnabled(true);
    }
    
    public static <T> T convertDtoToEntity(Object dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    public static <T> T convertEntityToDto(Object entity, Class<T> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

}

