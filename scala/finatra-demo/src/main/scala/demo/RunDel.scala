package demo

object RunDel extends App {
  val un = args(0)
  val u_id = args(1).toInt
  val c_id = args(2).toInt

  println(s"${un} ${u_id} ${c_id}")
  println("##################DB APP#####################")
  process(u_id, c_id)

  private def process(u_id: Int, c_id: Int): Unit = {
    println(s"delete from gb_dns_operation_log where user_id = ${u_id};")
    println(s"delete from gb_acc_package where user_id = ${u_id};")
    println(s"delete from gb_dns_domainitems where domain_id = (select id from gb_dns_domains where user_id=${u_id});")
    println(s"delete from gb_dns_domains where user_id = ${u_id};")
    println(s"delete from gb_co_areacode_sys where cac_id in (select id from gb_co_areacode_config where company_id = ${c_id});")
    println(s"delete from gb_co_areacode_config where company_id = ${c_id};")
    println(s"delete from gb_co_ip_manager where company_id = ${c_id};")
    println(s"delete from gb_dns_center_node where nodeservers_id in (select id from gb_dns_node_servers where company_id = ${c_id});")
    println(s"delete from gb_dns_node_servers where company_id = ${c_id};")
    println(s"delete from gb_dns_transactions where user_id=${u_id};")
    println(s"delete from gb_acc_log where user_id=${u_id};")
    println(s"delete from gb_dns_company_status where company_id= ${c_id};")
    println(s"delete from gb_dns_companies where userid=${u_id};")
    println(s"delete from gb_dns_users where username='${un}';")
    println("##################DB KEY#####################")
    println(s"delete from gu_co_areacode_config where co_id=${c_id};")
    println(s"delete from gu_co_ip_manager where co_id=${c_id};")
    println(s"delete from gu_dns_domainitems where domainid in (select id from gu_dns_domains where co_id=${c_id});")
    println(s"delete from gu_dns_domains where co_id=${c_id};")
    println(s"delete from gu_dns_center_node where nodeservers_id in (select id from gu_dns_node_servers where co_id=${c_id});")
    println(s"delete from gu_dns_node_servers where co_id=${c_id};")
    println(s"delete from gu_dns_users where id=${u_id};")
  }
}
