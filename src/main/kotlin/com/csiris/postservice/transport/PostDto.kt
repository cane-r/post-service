package com.csiris.postservice.transport

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

import com.csiris.postservice.persistence.Post;
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonCreator

//leveraging Kotlin data class..
@JsonAutoDetect(
	fieldVisibility = JsonAutoDetect.Visibility.ANY
)

data class PostDto @JsonCreator constructor(
	@field:NotNull(message = "{title.not.empty}") var title: String?,
	@field:NotNull(message = "{content.not.empty}") var content: String?,
	@JsonIgnore var approved: Boolean? = false,
	@JsonIgnore var processed: Boolean? = false,
	var id: Long?,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss") var createdAt: LocalDateTime?,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss") var updatedAt: LocalDateTime?
) {
	constructor () : this("", "", false, false, null, null, null)
}