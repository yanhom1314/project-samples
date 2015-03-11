package db

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = scala.slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: scala.slick.driver.JdbcProfile
  import profile.simple._

import scala.slick.jdbc.{GetResult => GR}
  import scala.slick.model.ForeignKeyAction
  
  /** DDL for all tables. Call .create to execute. */
  lazy val ddl = SAdmin.ddl ++ SAdminRole.ddl ++ SAppeal.ddl ++ SIgnore.ddl ++ SLog.ddl ++ SRole.ddl ++ STimeRule.ddl ++ SUser.ddl ++ SZone.ddl
  
  /** Entity class storing rows of table SAdmin
   *  @param id Database column id DBType(int8), PrimaryKey
   *  @param createdate Database column createdate DBType(timestamp), Default(None)
   *  @param loginname Database column loginname DBType(varchar), Length(50,true)
   *  @param loginpassword Database column loginpassword DBType(varchar), Length(50,true)
   *  @param zId Database column z_id DBType(int8), Default(None)
   *  @param lastlogindate Database column lastlogindate DBType(timestamp), Default(None)
   *  @param lastloginip Database column lastloginip DBType(varchar), Length(255,true), Default(None) */
  case class SAdminRow(id: Long, createdate: Option[java.sql.Timestamp] = None, loginname: String, loginpassword: String, zId: Option[Long] = None, lastlogindate: Option[java.sql.Timestamp] = None, lastloginip: Option[String] = None)
  /** GetResult implicit for fetching SAdminRow objects using plain SQL queries */
  implicit def GetResultSAdminRow(implicit e0: GR[Long], e1: GR[Option[java.sql.Timestamp]], e2: GR[String], e3: GR[Option[Long]], e4: GR[Option[String]]): GR[SAdminRow] = GR{
    prs => import prs._
    SAdminRow.tupled((<<[Long], <<?[java.sql.Timestamp], <<[String], <<[String], <<?[Long], <<?[java.sql.Timestamp], <<?[String]))
  }
  /** Table description of table s_admin. Objects of this class serve as prototypes for rows in queries. */
  class SAdmin(_tableTag: Tag) extends Table[SAdminRow](_tableTag, "s_admin") {
    def * = (id, createdate, loginname, loginpassword, zId, lastlogindate, lastloginip) <> (SAdminRow.tupled, SAdminRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, createdate, loginname.?, loginpassword.?, zId, lastlogindate, lastloginip).shaped.<>({r=>import r._; _1.map(_=> SAdminRow.tupled((_1.get, _2, _3.get, _4.get, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(int8), PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.PrimaryKey)
    /** Database column createdate DBType(timestamp), Default(None) */
    val createdate: Column[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("createdate", O.Default(None))
    /** Database column loginname DBType(varchar), Length(50,true) */
    val loginname: Column[String] = column[String]("loginname", O.Length(50,varying=true))
    /** Database column loginpassword DBType(varchar), Length(50,true) */
    val loginpassword: Column[String] = column[String]("loginpassword", O.Length(50,varying=true))
    /** Database column z_id DBType(int8), Default(None) */
    val zId: Column[Option[Long]] = column[Option[Long]]("z_id", O.Default(None))
    /** Database column lastlogindate DBType(timestamp), Default(None) */
    val lastlogindate: Column[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("lastlogindate", O.Default(None))
    /** Database column lastloginip DBType(varchar), Length(255,true), Default(None) */
    val lastloginip: Column[Option[String]] = column[Option[String]]("lastloginip", O.Length(255,varying=true), O.Default(None))
    
    /** Foreign key referencing SZone (database name fk_q51amhqyosvdpohm6fbscdnqt) */
    lazy val sZoneFk = foreignKey("fk_q51amhqyosvdpohm6fbscdnqt", zId, SZone)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    
    /** Uniqueness Index over (loginname) (database name uk_h0gy1l1byotbti4gccgfdcywc) */
    val index1 = index("uk_h0gy1l1byotbti4gccgfdcywc", loginname, unique=true)
  }
  /** Collection-like TableQuery object for table SAdmin */
  lazy val SAdmin = new TableQuery(tag => new SAdmin(tag))
  
  /** Entity class storing rows of table SAdminRole
   *  @param adminId Database column admin_id DBType(int8)
   *  @param roleId Database column role_id DBType(int8) */
  case class SAdminRoleRow(adminId: Long, roleId: Long)
  /** GetResult implicit for fetching SAdminRoleRow objects using plain SQL queries */
  implicit def GetResultSAdminRoleRow(implicit e0: GR[Long]): GR[SAdminRoleRow] = GR{
    prs => import prs._
    SAdminRoleRow.tupled((<<[Long], <<[Long]))
  }
  /** Table description of table s_admin_role. Objects of this class serve as prototypes for rows in queries. */
  class SAdminRole(_tableTag: Tag) extends Table[SAdminRoleRow](_tableTag, "s_admin_role") {
    def * = (adminId, roleId) <> (SAdminRoleRow.tupled, SAdminRoleRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (adminId.?, roleId.?).shaped.<>({r=>import r._; _1.map(_=> SAdminRoleRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column admin_id DBType(int8) */
    val adminId: Column[Long] = column[Long]("admin_id")
    /** Database column role_id DBType(int8) */
    val roleId: Column[Long] = column[Long]("role_id")
    
    /** Primary key of SAdminRole (database name s_admin_role_pkey) */
    val pk = primaryKey("s_admin_role_pkey", (adminId, roleId))
    
    /** Foreign key referencing SAdmin (database name fk_cwa3eljp1ta9ir7yx52d7t8ar) */
    lazy val sAdminFk = foreignKey("fk_cwa3eljp1ta9ir7yx52d7t8ar", adminId, SAdmin)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing SRole (database name fk_o5new72yqgqb6ut67jnw98kkg) */
    lazy val sRoleFk = foreignKey("fk_o5new72yqgqb6ut67jnw98kkg", roleId, SRole)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table SAdminRole */
  lazy val SAdminRole = new TableQuery(tag => new SAdminRole(tag))
  
  /** Entity class storing rows of table SAppeal
   *  @param id Database column id DBType(int8), PrimaryKey
   *  @param contact Database column contact DBType(varchar), Length(50,true)
   *  @param content Database column content DBType(varchar), Length(500,true)
   *  @param createdate Database column createdate DBType(timestamp), Default(None)
   *  @param ip Database column ip DBType(varchar), Length(255,true), Default(None)
   *  @param phone Database column phone DBType(varchar), Length(255,true), Default(None)
   *  @param usernumber Database column usernumber DBType(varchar), Length(50,true), Default(None)
   *  @param zone Database column zone DBType(varchar), Length(255,true), Default(None) */
  case class SAppealRow(id: Long, contact: String, content: String, createdate: Option[java.sql.Timestamp] = None, ip: Option[String] = None, phone: Option[String] = None, usernumber: Option[String] = None, zone: Option[String] = None)
  /** GetResult implicit for fetching SAppealRow objects using plain SQL queries */
  implicit def GetResultSAppealRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[java.sql.Timestamp]], e3: GR[Option[String]]): GR[SAppealRow] = GR{
    prs => import prs._
    SAppealRow.tupled((<<[Long], <<[String], <<[String], <<?[java.sql.Timestamp], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table s_appeal. Objects of this class serve as prototypes for rows in queries. */
  class SAppeal(_tableTag: Tag) extends Table[SAppealRow](_tableTag, "s_appeal") {
    def * = (id, contact, content, createdate, ip, phone, usernumber, zone) <> (SAppealRow.tupled, SAppealRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, contact.?, content.?, createdate, ip, phone, usernumber, zone).shaped.<>({r=>import r._; _1.map(_=> SAppealRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7, _8)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(int8), PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.PrimaryKey)
    /** Database column contact DBType(varchar), Length(50,true) */
    val contact: Column[String] = column[String]("contact", O.Length(50,varying=true))
    /** Database column content DBType(varchar), Length(500,true) */
    val content: Column[String] = column[String]("content", O.Length(500,varying=true))
    /** Database column createdate DBType(timestamp), Default(None) */
    val createdate: Column[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("createdate", O.Default(None))
    /** Database column ip DBType(varchar), Length(255,true), Default(None) */
    val ip: Column[Option[String]] = column[Option[String]]("ip", O.Length(255,varying=true), O.Default(None))
    /** Database column phone DBType(varchar), Length(255,true), Default(None) */
    val phone: Column[Option[String]] = column[Option[String]]("phone", O.Length(255,varying=true), O.Default(None))
    /** Database column usernumber DBType(varchar), Length(50,true), Default(None) */
    val usernumber: Column[Option[String]] = column[Option[String]]("usernumber", O.Length(50,varying=true), O.Default(None))
    /** Database column zone DBType(varchar), Length(255,true), Default(None) */
    val zone: Column[Option[String]] = column[Option[String]]("zone", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table SAppeal */
  lazy val SAppeal = new TableQuery(tag => new SAppeal(tag))
  
  /** Entity class storing rows of table SIgnore
   *  @param id Database column id DBType(int8), PrimaryKey
   *  @param createdate Database column createdate DBType(timestamp), Default(None)
   *  @param fromname Database column fromname DBType(varchar), Length(255,true), Default(None)
   *  @param usernumber Database column usernumber DBType(varchar), Length(255,true), Default(None) */
  case class SIgnoreRow(id: Long, createdate: Option[java.sql.Timestamp] = None, fromname: Option[String] = None, usernumber: Option[String] = None)
  /** GetResult implicit for fetching SIgnoreRow objects using plain SQL queries */
  implicit def GetResultSIgnoreRow(implicit e0: GR[Long], e1: GR[Option[java.sql.Timestamp]], e2: GR[Option[String]]): GR[SIgnoreRow] = GR{
    prs => import prs._
    SIgnoreRow.tupled((<<[Long], <<?[java.sql.Timestamp], <<?[String], <<?[String]))
  }
  /** Table description of table s_ignore. Objects of this class serve as prototypes for rows in queries. */
  class SIgnore(_tableTag: Tag) extends Table[SIgnoreRow](_tableTag, "s_ignore") {
    def * = (id, createdate, fromname, usernumber) <> (SIgnoreRow.tupled, SIgnoreRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, createdate, fromname, usernumber).shaped.<>({r=>import r._; _1.map(_=> SIgnoreRow.tupled((_1.get, _2, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(int8), PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.PrimaryKey)
    /** Database column createdate DBType(timestamp), Default(None) */
    val createdate: Column[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("createdate", O.Default(None))
    /** Database column fromname DBType(varchar), Length(255,true), Default(None) */
    val fromname: Column[Option[String]] = column[Option[String]]("fromname", O.Length(255,varying=true), O.Default(None))
    /** Database column usernumber DBType(varchar), Length(255,true), Default(None) */
    val usernumber: Column[Option[String]] = column[Option[String]]("usernumber", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table SIgnore */
  lazy val SIgnore = new TableQuery(tag => new SIgnore(tag))
  
  /** Entity class storing rows of table SLog
   *  @param id Database column id DBType(int8), PrimaryKey
   *  @param createdate Database column createdate DBType(timestamp), Default(None)
   *  @param ip Database column ip DBType(varchar), Length(50,true)
   *  @param url Database column url DBType(varchar), Length(1024,true)
   *  @param usernumber Database column usernumber DBType(varchar), Length(255,true), Default(None) */
  case class SLogRow(id: Long, createdate: Option[java.sql.Timestamp] = None, ip: String, url: String, usernumber: Option[String] = None)
  /** GetResult implicit for fetching SLogRow objects using plain SQL queries */
  implicit def GetResultSLogRow(implicit e0: GR[Long], e1: GR[Option[java.sql.Timestamp]], e2: GR[String], e3: GR[Option[String]]): GR[SLogRow] = GR{
    prs => import prs._
    SLogRow.tupled((<<[Long], <<?[java.sql.Timestamp], <<[String], <<[String], <<?[String]))
  }
  /** Table description of table s_log. Objects of this class serve as prototypes for rows in queries. */
  class SLog(_tableTag: Tag) extends Table[SLogRow](_tableTag, "s_log") {
    def * = (id, createdate, ip, url, usernumber) <> (SLogRow.tupled, SLogRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, createdate, ip.?, url.?, usernumber).shaped.<>({r=>import r._; _1.map(_=> SLogRow.tupled((_1.get, _2, _3.get, _4.get, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(int8), PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.PrimaryKey)
    /** Database column createdate DBType(timestamp), Default(None) */
    val createdate: Column[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("createdate", O.Default(None))
    /** Database column ip DBType(varchar), Length(50,true) */
    val ip: Column[String] = column[String]("ip", O.Length(50,varying=true))
    /** Database column url DBType(varchar), Length(1024,true) */
    val url: Column[String] = column[String]("url", O.Length(1024,varying=true))
    /** Database column usernumber DBType(varchar), Length(255,true), Default(None) */
    val usernumber: Column[Option[String]] = column[Option[String]]("usernumber", O.Length(255,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table SLog */
  lazy val SLog = new TableQuery(tag => new SLog(tag))
  
  /** Entity class storing rows of table SRole
   *  @param id Database column id DBType(int8), PrimaryKey
   *  @param roledesc Database column roledesc DBType(varchar), Length(255,true), Default(None)
   *  @param rolename Database column rolename DBType(varchar), Length(50,true) */
  case class SRoleRow(id: Long, roledesc: Option[String] = None, rolename: String)
  /** GetResult implicit for fetching SRoleRow objects using plain SQL queries */
  implicit def GetResultSRoleRow(implicit e0: GR[Long], e1: GR[Option[String]], e2: GR[String]): GR[SRoleRow] = GR{
    prs => import prs._
    SRoleRow.tupled((<<[Long], <<?[String], <<[String]))
  }
  /** Table description of table s_role. Objects of this class serve as prototypes for rows in queries. */
  class SRole(_tableTag: Tag) extends Table[SRoleRow](_tableTag, "s_role") {
    def * = (id, roledesc, rolename) <> (SRoleRow.tupled, SRoleRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, roledesc, rolename.?).shaped.<>({r=>import r._; _1.map(_=> SRoleRow.tupled((_1.get, _2, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(int8), PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.PrimaryKey)
    /** Database column roledesc DBType(varchar), Length(255,true), Default(None) */
    val roledesc: Column[Option[String]] = column[Option[String]]("roledesc", O.Length(255,varying=true), O.Default(None))
    /** Database column rolename DBType(varchar), Length(50,true) */
    val rolename: Column[String] = column[String]("rolename", O.Length(50,varying=true))
    
    /** Uniqueness Index over (rolename) (database name uk_lp6gec8gkqjcxel4gx0r4ki77) */
    val index1 = index("uk_lp6gec8gkqjcxel4gx0r4ki77", rolename, unique=true)
  }
  /** Collection-like TableQuery object for table SRole */
  lazy val SRole = new TableQuery(tag => new SRole(tag))
  
  /** Entity class storing rows of table STimeRule
   *  @param id Database column id DBType(int8), PrimaryKey
   *  @param ruledesc Database column ruledesc DBType(varchar), Length(500,true)
   *  @param ruleex Database column ruleex DBType(varchar), Length(100,true)
   *  @param uId Database column u_id DBType(int8) */
  case class STimeRuleRow(id: Long, ruledesc: String, ruleex: String, uId: Long)
  /** GetResult implicit for fetching STimeRuleRow objects using plain SQL queries */
  implicit def GetResultSTimeRuleRow(implicit e0: GR[Long], e1: GR[String]): GR[STimeRuleRow] = GR{
    prs => import prs._
    STimeRuleRow.tupled((<<[Long], <<[String], <<[String], <<[Long]))
  }
  /** Table description of table s_time_rule. Objects of this class serve as prototypes for rows in queries. */
  class STimeRule(_tableTag: Tag) extends Table[STimeRuleRow](_tableTag, "s_time_rule") {
    def * = (id, ruledesc, ruleex, uId) <> (STimeRuleRow.tupled, STimeRuleRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, ruledesc.?, ruleex.?, uId.?).shaped.<>({r=>import r._; _1.map(_=> STimeRuleRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(int8), PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.PrimaryKey)
    /** Database column ruledesc DBType(varchar), Length(500,true) */
    val ruledesc: Column[String] = column[String]("ruledesc", O.Length(500,varying=true))
    /** Database column ruleex DBType(varchar), Length(100,true) */
    val ruleex: Column[String] = column[String]("ruleex", O.Length(100,varying=true))
    /** Database column u_id DBType(int8) */
    val uId: Column[Long] = column[Long]("u_id")
    
    /** Foreign key referencing SUser (database name fk_j22p2f23a80nqy97tu175bn8v) */
    lazy val sUserFk = foreignKey("fk_j22p2f23a80nqy97tu175bn8v", uId, SUser)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table STimeRule */
  lazy val STimeRule = new TableQuery(tag => new STimeRule(tag))
  
  /** Entity class storing rows of table SUser
   *  @param id Database column id DBType(int8), PrimaryKey
   *  @param contact Database column contact DBType(varchar), Length(255,true), Default(None)
   *  @param createdate Database column createdate DBType(timestamp), Default(None)
   *  @param email Database column email DBType(varchar), Length(255,true), Default(None)
   *  @param flag Database column flag DBType(varchar), Length(255,true), Default(None)
   *  @param ip Database column ip DBType(varchar), Length(255,true), Default(None)
   *  @param phone Database column phone DBType(varchar), Length(255,true), Default(None)
   *  @param servicedate Database column servicedate DBType(timestamp), Default(None)
   *  @param userNumber Database column user_number DBType(varchar), Length(50,true)
   *  @param zoneId Database column zone_id DBType(int8), Default(None)
   *  @param online Database column online DBType(bool), Default(None)
   *  @param onlineip Database column onlineip DBType(varchar), Length(255,true), Default(None)
   *  @param onlinetime Database column onlinetime DBType(timestamp), Default(None)
   *  @param policypassword Database column policypassword DBType(varchar), Length(255,true), Default(None)
   *  @param master Database column master DBType(bool), Default(None) */
  case class SUserRow(id: Long, contact: Option[String] = None, createdate: Option[java.sql.Timestamp] = None, email: Option[String] = None, flag: Option[String] = None, ip: Option[String] = None, phone: Option[String] = None, servicedate: Option[java.sql.Timestamp] = None, userNumber: String, zoneId: Option[Long] = None, online: Option[Boolean] = None, onlineip: Option[String] = None, onlinetime: Option[java.sql.Timestamp] = None, policypassword: Option[String] = None, master: Option[Boolean] = None)
  /** GetResult implicit for fetching SUserRow objects using plain SQL queries */
  implicit def GetResultSUserRow(implicit e0: GR[Long], e1: GR[Option[String]], e2: GR[Option[java.sql.Timestamp]], e3: GR[String], e4: GR[Option[Long]], e5: GR[Option[Boolean]]): GR[SUserRow] = GR{
    prs => import prs._
    SUserRow.tupled((<<[Long], <<?[String], <<?[java.sql.Timestamp], <<?[String], <<?[String], <<?[String], <<?[String], <<?[java.sql.Timestamp], <<[String], <<?[Long], <<?[Boolean], <<?[String], <<?[java.sql.Timestamp], <<?[String], <<?[Boolean]))
  }
  /** Table description of table s_user. Objects of this class serve as prototypes for rows in queries. */
  class SUser(_tableTag: Tag) extends Table[SUserRow](_tableTag, "s_user") {
    def * = (id, contact, createdate, email, flag, ip, phone, servicedate, userNumber, zoneId, online, onlineip, onlinetime, policypassword, master) <> (SUserRow.tupled, SUserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, contact, createdate, email, flag, ip, phone, servicedate, userNumber.?, zoneId, online, onlineip, onlinetime, policypassword, master).shaped.<>({r=>import r._; _1.map(_=> SUserRow.tupled((_1.get, _2, _3, _4, _5, _6, _7, _8, _9.get, _10, _11, _12, _13, _14, _15)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(int8), PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.PrimaryKey)
    /** Database column contact DBType(varchar), Length(255,true), Default(None) */
    val contact: Column[Option[String]] = column[Option[String]]("contact", O.Length(255,varying=true), O.Default(None))
    /** Database column createdate DBType(timestamp), Default(None) */
    val createdate: Column[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("createdate", O.Default(None))
    /** Database column email DBType(varchar), Length(255,true), Default(None) */
    val email: Column[Option[String]] = column[Option[String]]("email", O.Length(255,varying=true), O.Default(None))
    /** Database column flag DBType(varchar), Length(255,true), Default(None) */
    val flag: Column[Option[String]] = column[Option[String]]("flag", O.Length(255,varying=true), O.Default(None))
    /** Database column ip DBType(varchar), Length(255,true), Default(None) */
    val ip: Column[Option[String]] = column[Option[String]]("ip", O.Length(255,varying=true), O.Default(None))
    /** Database column phone DBType(varchar), Length(255,true), Default(None) */
    val phone: Column[Option[String]] = column[Option[String]]("phone", O.Length(255,varying=true), O.Default(None))
    /** Database column servicedate DBType(timestamp), Default(None) */
    val servicedate: Column[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("servicedate", O.Default(None))
    /** Database column user_number DBType(varchar), Length(50,true) */
    val userNumber: Column[String] = column[String]("user_number", O.Length(50,varying=true))
    /** Database column zone_id DBType(int8), Default(None) */
    val zoneId: Column[Option[Long]] = column[Option[Long]]("zone_id", O.Default(None))
    /** Database column online DBType(bool), Default(None) */
    val online: Column[Option[Boolean]] = column[Option[Boolean]]("online", O.Default(None))
    /** Database column onlineip DBType(varchar), Length(255,true), Default(None) */
    val onlineip: Column[Option[String]] = column[Option[String]]("onlineip", O.Length(255,varying=true), O.Default(None))
    /** Database column onlinetime DBType(timestamp), Default(None) */
    val onlinetime: Column[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("onlinetime", O.Default(None))
    /** Database column policypassword DBType(varchar), Length(255,true), Default(None) */
    val policypassword: Column[Option[String]] = column[Option[String]]("policypassword", O.Length(255,varying=true), O.Default(None))
    /** Database column master DBType(bool), Default(None) */
    val master: Column[Option[Boolean]] = column[Option[Boolean]]("master", O.Default(None))
    
    /** Foreign key referencing SZone (database name fk_3pk8rxe6d9w48gscajrg90w4p) */
    lazy val sZoneFk = foreignKey("fk_3pk8rxe6d9w48gscajrg90w4p", zoneId, SZone)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    
    /** Index over (userNumber) (database name i_user_number) */
    val index1 = index("i_user_number", userNumber)
    /** Uniqueness Index over (userNumber) (database name uk_95sbmuh16n8ydxwox9xsq5smc) */
    val index2 = index("uk_95sbmuh16n8ydxwox9xsq5smc", userNumber, unique=true)
  }
  /** Collection-like TableQuery object for table SUser */
  lazy val SUser = new TableQuery(tag => new SUser(tag))
  
  /** Entity class storing rows of table SZone
   *  @param id Database column id DBType(int8), PrimaryKey
   *  @param zonedesc Database column zonedesc DBType(varchar), Length(255,true), Default(None)
   *  @param zonename Database column zonename DBType(varchar), Length(50,true)
   *  @param zoneval Database column zoneval DBType(varchar), Length(50,true) */
  case class SZoneRow(id: Long, zonedesc: Option[String] = None, zonename: String, zoneval: String)
  /** GetResult implicit for fetching SZoneRow objects using plain SQL queries */
  implicit def GetResultSZoneRow(implicit e0: GR[Long], e1: GR[Option[String]], e2: GR[String]): GR[SZoneRow] = GR{
    prs => import prs._
    SZoneRow.tupled((<<[Long], <<?[String], <<[String], <<[String]))
  }
  /** Table description of table s_zone. Objects of this class serve as prototypes for rows in queries. */
  class SZone(_tableTag: Tag) extends Table[SZoneRow](_tableTag, "s_zone") {
    def * = (id, zonedesc, zonename, zoneval) <> (SZoneRow.tupled, SZoneRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, zonedesc, zonename.?, zoneval.?).shaped.<>({r=>import r._; _1.map(_=> SZoneRow.tupled((_1.get, _2, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(int8), PrimaryKey */
    val id: Column[Long] = column[Long]("id", O.PrimaryKey)
    /** Database column zonedesc DBType(varchar), Length(255,true), Default(None) */
    val zonedesc: Column[Option[String]] = column[Option[String]]("zonedesc", O.Length(255,varying=true), O.Default(None))
    /** Database column zonename DBType(varchar), Length(50,true) */
    val zonename: Column[String] = column[String]("zonename", O.Length(50,varying=true))
    /** Database column zoneval DBType(varchar), Length(50,true) */
    val zoneval: Column[String] = column[String]("zoneval", O.Length(50,varying=true))
  }
  /** Collection-like TableQuery object for table SZone */
  lazy val SZone = new TableQuery(tag => new SZone(tag))
}