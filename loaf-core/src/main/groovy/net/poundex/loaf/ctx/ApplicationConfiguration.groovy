package net.poundex.loaf.ctx

import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import loaf.Command

@Configuration
class ApplicationConfiguration
{
	@Bean
	BeanPostProcessor commandProcessor(CommandRegistry commandRegistry)
	{
		return new BeanPostProcessor() {
			@Override
			Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
				return bean
			}

			@Override
			Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
				if(bean instanceof Command)
					commandRegistry.register(bean)

				return bean
			}
		}
	}
}
