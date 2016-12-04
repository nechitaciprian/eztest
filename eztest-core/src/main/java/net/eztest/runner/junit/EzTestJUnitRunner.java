package net.eztest.runner.junit;

import java.io.IOException;
import java.util.logging.Logger;

import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import cucumber.api.junit.Cucumber;
import net.eztest.EzTest;
import net.eztest.EzTestContext;

public class EzTestJUnitRunner extends Cucumber {
	
	private static Logger log = Logger.getLogger(EzTest.class.getCanonicalName());

	public EzTestJUnitRunner(Class clazz) throws InitializationError, IOException {
        super(clazz);
	}

	@Override
	protected Statement withBeforeClasses(final Statement statement) {
	 return new Statement(){
			@Override
			public void evaluate() throws Throwable {
				EzTestContext.init();
				statement.evaluate();
			}
			
		};
		
	}

	@Override
	protected Statement withAfterClasses(final Statement statement) {
		 return new Statement(){
				@Override
				public void evaluate() throws Throwable {
					statement.evaluate();
					EzTestContext.quit();
				}
				
			};
	}



}
