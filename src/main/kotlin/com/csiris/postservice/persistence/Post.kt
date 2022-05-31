package com.csiris.postservice.persistence

import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.EntityListeners;
import javax.validation.constraints.NotNull;

import com.csiris.postservice.persistence.listener.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@EntityListeners(PostAuditListener::class)
@DynamicUpdate
data class Post(
	var title: String,
	var content: String,
	var approved: Boolean? = false,
	var processed: Boolean? = false
) : BaseEntity() {
	constructor (title: String, content: String) : this(title, content, false, false)

	constructor () : this("", "", false, false)

	override fun toString(): String {
		return "Post ( '$title , $content , $approved' ," + super.toString();
	}

}