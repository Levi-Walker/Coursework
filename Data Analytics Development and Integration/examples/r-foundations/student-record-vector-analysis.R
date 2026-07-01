# Levi Walker
# Vector Assignment
# Jan 20

# Generate unique student IDs
studentsID <- sample(13000:14500,20, replace=F)

# Generate student ages
studentAges <- sample(17:25, 20, replace=T)

# Display the generated vectors
print(studentsID)
print(studentAges)

# Calculate age summary statistics
print(mean(studentAges))
print(median(studentAges))
print(min(studentAges))
print(max(studentAges))

# Combine IDs and ages into student records
studentRecords <- cbind(studentsID, studentAges)
print(studentRecords)

# Identify represented ages
print(unique(studentAges))

# Identify ages not represented in the sample
print(setdiff(17:25,studentAges))

# Identify duplicate ages
print(unique(studentAges[duplicated(studentAges)]))



# Select students older than 20
print(studentRecords[studentAges > 20])

# Select students between 18 and 20
print(studentRecords[studentAges > 18 & studentAges < 20])

# Visualize the age distribution
hist(studentAges, xlab = 'Student Ages', main = 'Student Age Frequencies', col = 'Orange')
