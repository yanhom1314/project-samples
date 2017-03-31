package com.greatbit.tags.web.beetl

import java.io.File

import org.apache.commons.io.Charsets
import org.beetl.core.resource.{ClasspathResourceLoader, FileResourceLoader}
import org.beetl.core.{Configuration, GroupTemplate}

case class BeetlConfigurationFactory(groupTemplate: GroupTemplate)

object BeetlConfigurationFactory {
  def apply(templateDir: String): BeetlConfigurationFactory = BeetlConfigurationFactory(new GroupTemplate(new ClasspathResourceLoader(templateDir + "/", Charsets.UTF_8.name()), Configuration.defaultConfiguration))

  def apply(templateDir: File): BeetlConfigurationFactory = BeetlConfigurationFactory(new GroupTemplate(new FileResourceLoader(templateDir.getAbsolutePath, Charsets.UTF_8.name()), Configuration.defaultConfiguration))

}