package org.zerock.j1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_sample")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Sample {
  
  @Id
  private String keyCol;

  private String first;

  private String last;

}
