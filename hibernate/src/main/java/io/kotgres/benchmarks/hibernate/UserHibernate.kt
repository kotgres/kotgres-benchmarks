package io.kotgres.benchmarks.hibernate

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users_with_id")
data class UserHibernate(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    val id: Int,
    @Column(name = "name")
    val name: String?,
    @Column(name = "age")
    val age: Int?,
    @Column(name = "date_created")
    val dateCreated: LocalDateTime?,
) {
    constructor() : this(-1, null, null, null)
}
