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

billing_lab <- visit |>
  left_join(billing, by = "visit_id") |>
  left_join(lab, by = "visit_id")

patient_level <- billing_lab |>
  # Group data by patient
  group_by(patient_id) |>
  mutate(
    # Count visits
    total_visits = length(unique(visit_id)),
    
    # Avoid double-counting billing rows after the lab join
    total_charges = sum(charge_amount[!duplicated(billing_id)], na.rm = TRUE),
    
    # Avoid double-counting labs after the billing join
    total_labs = sum(!duplicated(lab_id) & !is.na(lab_id)),
    
    # Calculate the average charge per visit
    avg_charge_per_visit = total_charges / total_visits
  ) |>
  # Remove patient grouping
  ungroup() |>
  
  # Select one summary row per patient
  select(patient_id, total_visits, total_charges, total_labs, avg_charge_per_visit) |>
  distinct() |>
  # Join patient details by patient_id
  left_join(patient, by = "patient_id")
