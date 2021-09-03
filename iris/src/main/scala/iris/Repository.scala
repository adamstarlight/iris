package iris

trait Repository[F[_], T] {
  def findAll(): FindQuery[F, T]
  def find(predicate: Predicate): FindQuery[F, T]
  def insert(document: T): UpdateQuery[F, T]
  def delete(document: T): DeleteQuery[F, T]
}
