# Board-intelij-gradle

intelij를 이용한 게시판 (댓글기능) 
DB : Maria DB 사용하였음

-- 게시판 테이블
CREATE TABLE `tb_post` (
    `id`            bigint(20)    NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `title`         varchar(100)  NOT NULL COMMENT '제목',
    `content`       varchar(3000) NOT NULL COMMENT '내용',
    `writer`        varchar(20)   NOT NULL COMMENT '작성자',
    `view_cnt`      int(11)       NOT NULL COMMENT '조회 수',
    `notice_yn`     tinyint(1)    NOT NULL COMMENT '공지글 여부',
    `delete_yn`     tinyint(1)    NOT NULL COMMENT '삭제 여부',
    `created_date`  datetime      NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
    `modified_date` datetime               DEFAULT NULL COMMENT '최종 수정일시',
    PRIMARY KEY (`id`)
) COMMENT '게시글';

-- 29강 댓글 테이블
create table tb_comment (
      id bigint not null auto_increment comment '댓글 번호 (PK)'
    , post_id bigint not null comment '게시글 번호 (FK)'
    , content varchar(1000) not null comment '내용'
    , writer varchar(20) not null comment '작성자'
    , delete_yn tinyint(1) not null comment '삭제 여부'
    , created_date datetime not null default CURRENT_TIMESTAMP comment '생성일시'
    , modified_date datetime comment '최종 수정일시'
    , primary key(id)
) comment '댓글';

- 29강 댓글과 게시판 간에 외래키 설정을 위한 수정 쿼리
alter table tb_comment add constraint fk_post_comment foreign key(post_id) references tb_post(id);

show full columns from tb_comment; -- 테이블 구조 확인 1 (코멘트 포함)

desc tb_comment; -- 테이블 구조 확인 2
--------------------------------------------------------------------------------------------------------------------
마지막으로 다음의 스크립트를 실행해서 DB(스키마)에 제약 조건이 정상적으로 추가되었는지 확인해 주세요.
select *
from information_schema.table_constraints
where table_name = 'tb_comment';

추가적으로, 특정 테이블이 아닌 DB(스키마)의 모든 제약 조건을 확인하고 싶을 때는 조건절에 table_schema = '스키마 이름'을 입력해 주시면 됩니다.
