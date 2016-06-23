package repository

trait Repository[T, ID] {
  def findById(id: ID): T
}
