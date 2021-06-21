package com.pzwpj.projekt;

import com.pzwpj.projekt.dto.ReactionDto;
import com.pzwpj.projekt.model.Reaction;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class ProjektApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjektApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		/*PropertyMap<ReactionDto, Reaction> reactionMap = new PropertyMap<ReactionDto, Reaction>() {
			protected void configure() {
				map().getDogePost().setDogePostId(source.getDogePostId());
				map().getDoge().setUsername(source.getDogeUsername());
				skip().getDogePost().setDoge(null);
				skip().setReactionId(null);
				skip().getDoge().setDogeId(null);
			}
		};
*/

		return new ModelMapper();
	}
}
