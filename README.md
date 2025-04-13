# swiss-re-restructure

# Employee Hierarchy Analyzer

This Java application analyzes an employee hierarchy from a CSV file. It detects:
- Managers who are underpaid or overpaid based on subordinates' salaries
- Employees who have too many levels above them in the hierarchy (deep reporting chains)

## How to Build

```bash
# Clone the repository
$ git clone <repo-url>
$ cd swiss-re-restructure

# Build the application using Maven
$ mvn clean package
```

## How to Run

```bash
# Run the application with a CSV file path as argument
$ java -jar target/swiss-re-restructure-1.0-SNAPSHOT.jar /path/to/Employees.csv
```

## CSV Format

CSV should have the following header:
```
Id,firstName,lastName,salary,managerId
```

- `managerId` can be empty or -1 for CEO.

## Example Output
```
CEO: Alice Smith

Managers earning less than they should:
Manager: Carol White, Underpaid by: 15000.00

Managers earning more than they should:
Manager: Martin Chekov, Overpaid by: 10000.00

Employees with long reporting chains (>4 levels):
Employee: Ian Green, Managers Above: 5, Exceeds By: 1
```
