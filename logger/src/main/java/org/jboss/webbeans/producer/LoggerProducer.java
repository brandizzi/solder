/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.webbeans.producer;

import javax.context.ApplicationScoped;
import javax.inject.Current;
import javax.inject.DefinitionException;
import javax.inject.Produces;
import javax.inject.manager.InjectionPoint;

import org.jboss.webbeans.annotation.Logger;
import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logging;

/**
 * The <code>LoggerProducer</code> provides a producer method for all
 * @Logger annotated log objects.  Each logger is application scoped
 * since the logger applies to the class, not each instance of the
 * class.
 * 
 * @author David Allen
 *
 */
public class LoggerProducer
{
   @Produces @ApplicationScoped
   public Log produceLog(@Current InjectionPoint injectionPoint)
   {
      Log log = null;
      String category = null;
      if (injectionPoint.getType().equals(Log.class))
      {
         throw new DefinitionException("Cannot use @Logger on any type other than org.jboss.webbeans.log.Log: " + injectionPoint);
      }
      category = injectionPoint.getAnnotation(Logger.class).value();
      if (category == null)
      {
         log = Logging.getLog((Class<?>) injectionPoint.getMember().getDeclaringClass());
      }
      else
      {
         log = Logging.getLog(category);
      }
      return log;
   }
}
