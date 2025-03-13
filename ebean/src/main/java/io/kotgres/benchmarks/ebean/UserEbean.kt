package io.kotgres.benchmarks.ebean

import io.ebean.annotation.Identity
import io.ebean.annotation.IdentityType
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users_with_id")
data class UserEbean(
    @Id
    @Identity(type = IdentityType.IDENTITY)
    val id: Int? = null,
    @Column(name = "name")
    val name: String?,
    @Column(name = "age")
    val age: Int?,
    @Column(name = "date_created")
    val dateCreated: LocalDateTime?,
) {
    constructor() : this(-1, null, null, null)
}