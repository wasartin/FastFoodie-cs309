package com.will.exp3;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class Redirect extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	  public Redirect() {
	      super();
	      setUseReferer(true);
	  }
}
