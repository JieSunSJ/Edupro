CREATE TABLE IF NOT EXISTS dept (
                                    id          INT AUTO_INCREMENT PRIMARY KEY,
                                    name        VARCHAR(50)  NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS emp (
                                   id          INT AUTO_INCREMENT PRIMARY KEY,
                                   username    VARCHAR(50)  NOT NULL,
    password    VARCHAR(100) NOT NULL,
    role        INT          NOT NULL COMMENT '1:管理员, 2:普通用户',
    name        VARCHAR(50)  NOT NULL,
    gender      INT          COMMENT '1:男, 2:女',
    phone       VARCHAR(20),
    job         INT          COMMENT '1:班主任,2:讲师,3:学工主管,4:教研主管,5:咨询师',
    salary      DECIMAL(10,2),
    image       VARCHAR(255),
    entry_date  DATE,
    dept_id     INT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS clazz (
                                     id          INT AUTO_INCREMENT PRIMARY KEY,
                                     name        VARCHAR(50) NOT NULL,
    room        VARCHAR(50),
    begin_date  DATE,
    end_date    DATE,
    master_id   INT,
    subject     INT,
    status      INT DEFAULT 0 COMMENT '0:未开课, 1:已开课, 2:已完结',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS student (
                                       id              INT AUTO_INCREMENT PRIMARY KEY,
                                       name            VARCHAR(50) NOT NULL,
    no              VARCHAR(50),
    gender          INT COMMENT '1:男, 2:女',
    phone           VARCHAR(20),
    id_card         VARCHAR(20),
    is_college      INT COMMENT '1:是, 0:否',
    address         VARCHAR(255),
    degree          INT COMMENT '1:初中,2:高中,3:大专,4:本科,5:硕士,6:博士',
    graduation_date DATE,
    clazz_id        INT,
    violation_count SMALLINT DEFAULT 0,
    violation_score SMALLINT DEFAULT 0,
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS emp_expr (
                                        id      INT AUTO_INCREMENT PRIMARY KEY,
                                        emp_id  INT,
                                        begin   DATE,
                                        end     DATE,
                                        company VARCHAR(100),
    job     VARCHAR(50)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS operate_log (
                                           id             INT AUTO_INCREMENT PRIMARY KEY,
                                           operate_emp_id INT,
                                           operate_time   DATETIME,
                                           class_name     VARCHAR(255),
    method_name    VARCHAR(255),
    method_params  TEXT,
    return_value   TEXT,
    cost_time      BIGINT
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS emp_log (
                                       id           INT AUTO_INCREMENT PRIMARY KEY,
                                       operate_time DATETIME,
                                       info         TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;