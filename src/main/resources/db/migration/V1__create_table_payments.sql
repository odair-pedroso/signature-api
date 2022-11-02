
CREATE TABLE payments(
    id serial PRIMARY KEY,
    amount decimal(19,2),
    payment_type varchar(20),
    payment_code varchar(20),
    schedule_id varchar(20),
    created_at varchar(20),
    updated_at varchar(20)
);