# Memory Pagination Simulator

## Project ##

### Specifications ###

Language: **Java JDK 8**

Input data: **http://traces.cs.umass.edu/index.php/CpuMem/CpuMem**

Pagination size: **2¹² = 4096 bytes (4MB)**

Number of bits to displacement: **12 bits**

Number of bits to address: **20 bits**

Size of logic adress: **32 bits**

### Use ###

The program may be call using:

`java -jar program.jar [file][method|all][size]`

Where:

1. `java -jar` Invokes the JRE to run the program.

2. `program.jar` The built program location.

3. `file` The input file.

 4.`method|all` Select which method will be executed( this policies will be decided).  

5. `size` The size of each page in bytes, the input consists of an integer which represents a power of two number. 

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
This represents the memory access, after will be implemented an **reference string** considering the paging size and address.
