package org.example.entities

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    var id: Long = 0,

    @Column(name = "name", nullable = false)
    var name: String = "",

    @Column(name = "email", nullable = false, unique = true)
    var email: String = "",

    @Column(name = "password", nullable = false)
    var password: String = ""
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User
        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return "User(id=$id, name='$name', email='$email')"
    }
}
