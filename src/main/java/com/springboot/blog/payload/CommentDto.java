package com.springboot.blog.payload;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Api(value = "Comment model information")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    @ApiModelProperty(value = "Comment id")
    private Long id;

    @ApiModelProperty(value = "Comment name")
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @ApiModelProperty(value = "Comment email")
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    @ApiModelProperty(value = "Comment body")
    @NotEmpty
    @Size(min = 10, message = "Comment must be minimum 10 character")
    private String body;
}
