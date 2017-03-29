package com.example.jdbi

import javax.inject.{Inject, Singleton}

import finatra.greatbit.jdbi.DBIWrapper
import org.skife.jdbi.v2.DBI

@Singleton
case class DBIWrapperImpl @Inject()(val dbi: DBI) extends DBIWrapper
