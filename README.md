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

`java -jar program.jar [file][method|all][number of frames][size]`

Where:

  1. `java -jar` Invokes the JRE to run the program.

  2. `program.jar` The built program location.

  3. `file` The input file.

  4.`method|all` Select which method will be executed (fifo or optimal).  

  5. `number of frames` The user select how many frames the memory has.   

  6. `size` The size of each page in bytes, the input consists of an integer which represents a power of two number, but has a default value if the user does not put it. 

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
In case of the input value does not have 8 hexadecimal digits which represents 32 bits, the program fills zeros while the size of values are less than 8.

## Output ## 

Are expected two differents outputs

1. Page Substitution
```
---------------------------------------
| Deallocated      | Allocated        | 
---------------------------------------
| Page 1           | Page 2           | 
---------------------------------------
```
2. Final Results
```
Method: FIFO
Number of frame: 8
Page size: 4096
Displacement: 12 bits
Address: 20 bits
----------------------------------------
| Number of page fault |       20      |
----------------------------------------
```

