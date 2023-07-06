package org.zerock.j1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// @ToString
public class BoradListRcntDTO {
  
  private Long bno;

  private String title;

  private String writer;

  private long replyCount;

}
