package com.bookmark.entity

import com.bookmark.model.Group
import com.bookmark.model.GroupContext
import com.bookmark.model.GroupUser
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "T_GROUP")
@Entity
data class GroupEntity(
        @Id
        @Column(name = "GROUP_ID")
        @GeneratedValue(generator = "T_GROUP_SEQUENCE", strategy = GenerationType.AUTO)
        @SequenceGenerator(name = "T_GROUP_SEQUENCE", sequenceName = "T_GROUP_SEQUENCE", allocationSize = 1)
        val groupId: Long? = null,

        @Column(name = "GROUP_NAME")
        var groupName: String? = null,

        @Column(name = "GROUP_URL")
        val groupUrl: String? = null,

        @Column(name = "GROUP_CONTEXT")
        var groupContext: String? = null,

        @Column(name = "GROUP_CONTEXT_NAME")
        var groupContextName: String? = null,

        @Column(name = "CREATED_ON")
        val createdOn: LocalDateTime? = null,

        @OneToMany(
                cascade = [CascadeType.ALL],
                fetch = FetchType.EAGER,
                mappedBy = "group"
        )
        @Fetch(value = FetchMode.SUBSELECT)
        var users: List<GroupUserEntity> = emptyList(),

        @OneToMany(
                cascade = [CascadeType.ALL],
                fetch = FetchType.EAGER,
                mappedBy = "group"
        )
        @Fetch(value = FetchMode.SUBSELECT)
        var urls: List<GroupUrlEntity> = emptyList()

) {
    fun toDto(): Group {
        return Group(
                groupId = groupId,
                groupName = groupName,
                users = users.map {
                    GroupUser(
                            groupId = it.group?.groupId,
                            roleName = it.roleName,
                            email = it.email,
                            userName = it.userName,
                            userId = it.userId
                    )
                },
                groupContextName = groupContextName,
                groupContext = groupContext?.let { GroupContext.valueOf(it) },
                createdOn = createdOn
        )
    }
}