# Memory Pagination Simulator

## Project ##

### Specifications ###

Language: **Java JDK 8**

Additional Libraries: **https://github.com/stleary/JSON-java**

Input data: **http://traces.cs.umass.edu/index.php/CpuMem/CpuMem**

Pagination size: **Define**

Number of bits to displacement: **Define**

Number of bits to address: **Define**

Size of logic adress: **32 bits**

### Use ###

The program may be call using:

`java -jar program.jar [file][frames][method|all]`

Where:

1. `java -jar` Invokes the JRE to run the program.

2. `program.jar` The built program location.

3. `file` The input file.

4. `frames` Number of frames for pagination. 

5. `method|all` Select which method will be executed( this policies will be decided). 

The input file is a **txt** available on **Specification**:

```
  B5E723DC
  B5E723DC
  FEDD98DD
  B5E723DC
  B5E723DC
  B5E723DC
  B5E723DC
  B5E723DC
```
This represents the memory access, after will be implemented an ** reference string ** considering the paging size and address.
