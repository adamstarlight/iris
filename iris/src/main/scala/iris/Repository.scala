package iris

trait Repository[F[_], T] {
  type PredicateCtx

  def findAll(): FindQuery[F, T]
  def find(
      predicate: PredicateDSL[PredicateCtx] => PredicateCtx
  ): FindQuery[F, T]

  def insert(document: T): UpdateQuery[F, T]
  def delete(document: T): DeleteQuery[F, T]
}
