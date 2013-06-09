/*
 * Demoiselle Framework
 * Copyright (C) 2010 SERPRO
 * ----------------------------------------------------------------------------
 * This file is part of Demoiselle Framework.
 * 
 * Demoiselle Framework is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License version 3
 * along with this program; if not,  see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 * ----------------------------------------------------------------------------
 * Este arquivo é parte do Framework Demoiselle.
 * 
 * O Framework Demoiselle é um software livre; você pode redistribuí-lo e/ou
 * modificá-lo dentro dos termos da GNU LGPL versão 3 como publicada pela Fundação
 * do Software Livre (FSF).
 * 
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA
 * GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou
 * APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU/LGPL em português
 * para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da GNU LGPL versão 3, sob o título
 * "LICENCA.txt", junto com esse programa. Se não, acesse <http://www.gnu.org/licenses/>
 * ou escreva para a Fundação do Software Livre (FSF) Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02111-1301, USA.
 */

package br.gov.frameworkdemoiselle.component.checker.bootstrap;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.slf4j.Logger;

import br.gov.frameworkdemoiselle.component.checker.annotations.CheckOnDelete;
import br.gov.frameworkdemoiselle.component.checker.annotations.CheckOnInsert;
import br.gov.frameworkdemoiselle.component.checker.annotations.CheckOnSave;
import br.gov.frameworkdemoiselle.component.checker.annotations.CheckOnUpdate;
import br.gov.frameworkdemoiselle.internal.producer.LoggerProducer;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.util.Reflections;

public class CheckerBootstrap implements Extension {
	private Logger logger;

	public <X> void process(@Observes ProcessAnnotatedType<X> pat) {
		if (pat.getAnnotatedType().getJavaClass().isAnnotationPresent(BusinessController.class)
				&& Reflections.isOfType(pat.getAnnotatedType().getJavaClass(), DelegateCrud.class)) {

			/*
			 * Percorre o BC em busca de metodos anotados com as anotações de
			 * checagem
			 */
			for (AnnotatedMethod<?> am : pat.getAnnotatedType().getMethods()) {
				if (am.getAnnotation(CheckOnSave.class) != null || am.getAnnotation(CheckOnDelete.class) != null
						|| am.getAnnotation(CheckOnInsert.class) != null || am.getAnnotation(CheckOnUpdate.class) != null) {

				}
			}
		}
	}

	protected Logger getLogger() {
		if (logger == null) {
			logger = LoggerProducer.create(CheckerBootstrap.class);
		}
		return logger;
	}
}
