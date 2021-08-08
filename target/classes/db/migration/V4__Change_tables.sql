ALTER TABLE employer
    ADD COLUMN approved boolean;
ALTER TABLE resume
    ADD COLUMN approved boolean;
ALTER TABLE seeker
    ADD COLUMN approved boolean;
ALTER TABLE vacancy
    ADD COLUMN approved boolean;
ALTER TABLE usr
    ADD COLUMN hidden boolean;