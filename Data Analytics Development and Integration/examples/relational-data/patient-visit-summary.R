# install.packages("dplyr")
# install.packages("readxl")
library(dplyr)
library(readxl)

# Get data from file Feb17_data_for_joins.xlsx

my_file = file.choose()

patient <- read_excel(my_file, sheet ="Patient")
lab <- read_excel(my_file, sheet ="Lab")
billing <- read_excel(my_file, sheet ="Billing")
visit <- read_excel(my_file, sheet ="Visit")


my_visit <- visit |>
  left_join(patient, by = "patient_id") |>
  left_join(lab, by = "visit_id", relationship = "many-to-many") |>
  left_join(billing, by = "visit_id", relationship = "many-to-many") |>
  group_by(visit_id) |>
  mutate(
    num_labs = sum(!duplicated(lab_id) & !is.na(lab_id)),
    total_billing = sum(charge_amount[!duplicated(billing_id)], na.rm = TRUE)
  ) |>
  distinct(visit_id, name, visit_date, num_labs, total_billing)

my_visit
