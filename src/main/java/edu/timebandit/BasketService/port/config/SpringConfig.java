package edu.timebandit.BasketService.port.config;

import edu.timebandit.BasketService.core.domain.model.Watch;
import edu.timebandit.BasketService.port.product.dtos.WatchDTO;
import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Configuration
public class SpringConfig {

    @Bean
    @Qualifier("ModelMapper")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    @Qualifier("BasketModelMapper")
    public ModelMapper BasketModelMapper() {
        ModelMapper mapper = new ModelMapper();
        TypeMap<WatchDTO, Watch> typeMap = mapper.createTypeMap(WatchDTO.class, Watch.class);
        Converter<String, UUID> toUUID = ctx -> ctx.getSource() == null ? null : UUID.fromString(ctx.getSource());
        typeMap.addMappings(m -> m.using(toUUID).map(WatchDTO::getId, Watch::setId));
        return mapper;
    }
}
