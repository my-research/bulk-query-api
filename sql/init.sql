-- Step 1: 이름 저장 테이블 생성
CREATE TABLE names (
                       id SERIAL PRIMARY KEY,
                       name TEXT
);

-- Step 2: 예시 이름 데이터 삽입
INSERT INTO names (name)
VALUES
    ('Ann'),
    ('Bill'),
    ('Cindy'),
    ('Diane'),
    ('Emma');

-- Step 3: 고객 데이터 테이블 생성
CREATE TABLE person_data (
                             id INTEGER PRIMARY KEY,
                             name TEXT,
                             age INTEGER
);

-- Step 4: generate_series를 사용하여 100만 개의 데이터 삽입
WITH tmp AS (
    SELECT
        generate_series(1, 1000000) AS id,  -- 1부터 100만까지의 id 생성
        (random() * 4 + 1)::integer AS idx, -- 랜덤한 이름을 선택하기 위한 인덱스 (1-5)
            (random() * 79 + 1)::integer AS age -- 랜덤한 나이 생성 (1-80)
)
INSERT INTO person_data (id, name, age)
SELECT
    tmp.id,
    (SELECT name FROM names WHERE names.id = tmp.idx) AS name,
    tmp.age
FROM tmp;

-- 데이터 검증
-- select count(*) from person_data;
-- select * from person_data limit 10;
