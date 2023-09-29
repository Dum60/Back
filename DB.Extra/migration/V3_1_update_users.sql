ALTER TABLE person.users
    ADD company_id INTEGER;
ALTER TABLE person.users
    ADD FOREIGN KEY (company_id) references carmanufacturing.company (id);
