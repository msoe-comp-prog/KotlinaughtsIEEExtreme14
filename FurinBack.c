/**
 * @author Nicholas Johnson
 * 
 * Score 3/4 (75%)
 */

#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

static uint8_t read() {
    int temp = getchar();
    return (uint8_t)(temp != EOF ? temp : 0);
}

static void eatline() {
    char tmp;
    while (1) {
        scanf("%c", &tmp);
        if (tmp == '\n') {
            break;
        }
    }
}

int run();

int main(void) {
    int times;
    scanf("%d", &times);
    eatline();
    while (times--) {
        run();
        printf("\n");
        eatline();
    }
    return 0;
}

int run() {
    uint8_t mem[50] = {0};
    uint8_t *p = &mem[0];

    p[1] = read();
    p[2] = read();
    p[3] = read();
    p[3] -= p[2];
    p[2] -= p[1];
	
	//first loop
    p[14] = p[1]&1;
	
    p[6] = 2;
	

	
	
	if(p[14] == 1){
        p[13] = 0;
        p[14] = 0;
		p[15] = 0;
		p[16] = 0;
		p[17] = 255;
        p += 13;	
	} else {
        p[14] = p[1] - 1;
	    p[15] = p[2];
        p[16] = p[3];
		
		p[6] = 3;
		p[10] = 2;
		p[9] = 255;
	
		
		
		p += 9;
			
			
			
            
            while (*p != 0) {
                p[0] = 1;
                p++;
                if (*p != 0) {
                    p[-1]--;
                    p--;
                }
                p--;
                while (*p != 0) {
                    p[0]--;
                    p[2]--;
                    p--;
                }
                p[2]--;
                p[5]++;
                p += 6;
                while (*p != 0) {
                    p[-1]--;
                    p--;
                }
                p--;
                while (*p != 0) {
                    p[0]--;
                    p[1]++;
                    p += 2;
                    while (*p != 0) {
                        p[0]--;
                        p[-1]--;
                        p--;
                    }
                    p--;
                    while (*p != 0) {
                        p += 2;
                        while (*p != 0) {
                            p[0]--;
                            p[-2]--;
                            p--;
                        }
                        p[0]--;
                        p -= 2;
                        while (*p != 0) {
                            p[0]--;
                            p[1]--;
                            p[3]--;
                            p--;
                        }
                    }
                    p--;
                }
                p[2]--;
                p[-3]++;
                p -= 2;
                while (*p != 0) {
                    p[-1]--;
                    p--;
                }
                p--;
                while (*p != 0) {
                    p += 2;
                    while (*p != 0) {
                        p[-2]--;
                        p--;
                    }
                    p -= 2;
                    while (*p != 0) {
                        p--;
                    }
                }
                p[1]--;
                p++;
            }
            p[8]++;
            p += 8;		

		
        
        
        
        //stupid loop
        while (*p != 0) {
            p[0]--;
            p[-7] += p[-11];
			p[-11] += p[-9];
            p[-9] = 0;
			
			p[-6] += p[-10];
            p[-10] += p[-8];
            p[-8] = 1;
            p -= 8;
            while (*p != 0) {
                p[0] = 1;
                p++;
                if (*p != 0) {
                    p[-1]--;
                    p--;
                }
                p--;
                while (*p != 0) {
                    p[0]--;
                    p[2]--;
                    p--;
                }
                p[2]--;
                p[5]++;
                p += 6;
                while (*p != 0) {
                    p[-1]--;
                    p--;
                }
                p--;
                while (*p != 0) {
                    p[0]--;
                    p[1]++;
                    p += 2;
                    while (*p != 0) {
                        p[0]--;
                        p[-1]--;
                        p--;
                    }
                    p--;
                    while (*p != 0) {
                        p += 2;
                        while (*p != 0) {
                            p[0]--;
                            p[-2]--;
                            p--;
                        }
                        p[0]--;
                        p -= 2;
                        while (*p != 0) {
                            p[0]--;
                            p[1]--;
                            p[3]--;
                            p--;
                        }
                    }
                    p--;
                }
                p[2]--;
                p[-3]++;
                p -= 2;
                while (*p != 0) {
                    p[-1]--;
                    p--;
                }
                p--;
                while (*p != 0) {
                    p += 2;
                    while (*p != 0) {
                        p[-2]--;
                        p--;
                    }
                    p -= 2;
                    while (*p != 0) {
                        p--;
                    }
                }
                p[1]--;
                p++;
            }
            p[8]++;
            p += 8;
        }
        p[0]--;
        p[-4]++;
        p[-3]++;
        p -= 3;
        while (*p != 0) {
            p[-1]--;
            p--;
        }
        p--;
        while (*p != 0) {
            p[2]++;
            p += 2;
            while (*p != 0) {
                p[-2]--;
                p--;
            }
            p -= 2;
            while (*p != 0) {
                p[3]++;
                p += 3;
                while (*p != 0) {
                    p[-3]--;
                    p--;
                }
                p -= 3;
                while (*p != 0) {
                    p--;
                }
            }
        }
        p[1]--;
        p++;		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
    }
	
	// actual loop version
    while (*p != 0) {
//	printf("FUCK");
	    p[0] = 0;
		p[1] = p[-12];
		p[2] = p[-11];
		p[3] = p[-10];
        p[4] = 0;
        p[-5] += p[-12];
        p[-4] += p[-11];
        p[-3] += p[-10];
		p[-12] = 0;
        p[-11] = 0;
        p[-10] = 0;
        p[-8]++;
        p[-7]++;

        p -= 7;
		// [0,0,0,?,?+1,?+1]
        while (*p != 0) {
		    p--;
            p[0]--;
        }
        p--;
        while (*p != 0) {
            p[0]--;
            p[2]++;
            p--;
        }
        p[-3] += p[4];
        p[4] = 0;
        p[-2] += p[5];
        p[5] = 0;
        p[-1] += p[6];
        p[6] = 0;
        p[13]++;
        p += 13;
		//stoooopid loop
        while (*p != 0) {
            p[0]--;
            p[-7] += p[-11];
			p[-11] += p[-9];
            p[-9] = 0;
			
			p[-6] += p[-10];
            p[-10] += p[-8];
            p[-8] = 1;
            p -= 8;
            while (*p != 0) {
                p[0] = 1;
                p++;
                if (*p != 0) {
                    p[-1]--;
                    p--;
                }
                p--;
                while (*p != 0) {
                    p[0]--;
                    p[2]--;
                    p--;
                }
                p[2]--;
                p[5]++;
                p += 6;
                while (*p != 0) {
                    p[-1]--;
                    p--;
                }
                p--;
                while (*p != 0) {
                    p[0]--;
                    p[1]++;
                    p += 2;
                    while (*p != 0) {
                        p[0]--;
                        p[-1]--;
                        p--;
                    }
                    p--;
                    while (*p != 0) {
                        p += 2;
                        while (*p != 0) {
                            p[0]--;
                            p[-2]--;
                            p--;
                        }
                        p[0]--;
                        p -= 2;
                        while (*p != 0) {
                            p[0]--;
                            p[1]--;
                            p[3]--;
                            p--;
                        }
                    }
                    p--;
                }
                p[2]--;
                p[-3]++;
                p -= 2;
                while (*p != 0) {
                    p[-1]--;
                    p--;
                }
                p--;
                while (*p != 0) {
                    p += 2;
                    while (*p != 0) {
                        p[-2]--;
                        p--;
                    }
                    p -= 2;
                    while (*p != 0) {
                        p--;
                    }
                }
                p[1]--;
                p++;
            }
            p[8]++;
            p += 8;
        }
        p[0]--;
        p[-4]++;
        p[-3]++;
        p -= 3;
        while (*p != 0) {
            p[-1]--;
            p--;
        }
        p--;
        while (*p != 0) {
            p[2]++;
            p += 2;
            while (*p != 0) {
                p[-2]--;
                p--;
            }
            p -= 2;
            while (*p != 0) {
                p[3]++;
                p += 3;
                while (*p != 0) {
                    p[-3]--;
                    p--;
                }
                p -= 3;
                while (*p != 0) {
                    p--;
                }
            }
        }
        p[1]--;
        p++;
    }
    p[4] = 0;
    p[-10] = 0;
    p[-8]++;
    p[-7]++;
    p -= 7;
    while (*p != 0) {
        p[-1]--;
        p--;
    }
    p--;
    while (*p != 0) {
        p[0]--;
        p[2]++;
        p--;
    }
    p[1]++;
    p += 2;
    while (*p != 0) {
        p[-1]--;
        p--;
    }
    p--;
    while (*p != 0) {
        p += 2;
        while (*p != 0) {
            p[-2]--;
            p--;
        }
        p -= 2;
        while (*p != 0) {
            p--;
        }
    }
    p[1]--;
    p++;
    while (*p != 0) {
        p[0]++;
        p[6] += 10;
        p[0]++;
        p++;
        while (*p != 0) {
            p[-1]--;
            p--;
        }
        p--;
        while (*p != 0) {
            p += 2;
            while (*p != 0) {
                p[-2]--;
                p--;
            }
            p -= 2;
            while (*p != 0) {
                p--;
            }
        }
        p[1]--;
        p++;
        while (*p != 0) {
            p[0] += 2;
            p++;
            while (*p != 0) {
                p[-1]--;
                p--;
            }
            p--;
            while (*p != 0) {
                p[0]--;
                p[2]--;
                p--;
            }
            p[2]--;
            p[4]++;
            p[6]++;
            p[7]--;
            p += 7;
            while (*p != 0) {
                p[-1]--;
                p--;
            }
            p--;
            while (*p != 0) {
                p[1] += p[-2];
                p[-2] = 0;
                p[3]++;
                p[4]++;
                p += 4;
                while (*p != 0) {
                    p[-1]--;
                    p--;
                }
                p--;
                while (*p != 0) {
                    p[0]--;
                    p[2]++;
                    p--;
                }
                p[-2]--;
                p -= 3;
            }
            p[-4]++;
            p -= 3;
            while (*p != 0) {
                p[-1]--;
                p--;
            }
            p--;
            while (*p != 0) {
                p += 2;
                while (*p != 0) {
                    p[-2]--;
                    p--;
                }
                p -= 2;
                while (*p != 0) {
                    p--;
                }
            }
            p[1]--;
            p++;
        }
        p[2] += 8;
        p[3] += p[2] * 6;
        p[2] = 0;
        p[-1] += p[3];
        p[3] = 0;
        p[8]++;
        p += 9;
        while (*p != 0) {
            p[-1]--;
            p--;
        }
        p--;
        while (*p != 0) {
            p += 2;
            while (*p != 0) {
                p[-2]--;
                p--;
            }
            p -= 2;
            while (*p != 0) {
                p--;
            }
        }
        p[1]--;
        p++;
        while (*p != 0) {
            p[0] += 2;
            p++;
            while (*p != 0) {
                p[-1]--;
                p--;
            }
            p--;
            while (*p != 0) {
                p[0]--;
                p[2]--;
                p--;
            }
            p[2]--;
            p[-6]++;
            p[-5]++;
            p -= 5;
            while (*p != 0) {
                p[-1]--;
                p--;
            }
            p--;
            while (*p != 0) {
                p[0]--;
                p[2]++;
                p--;
            }
            p[8]++;
            p += 9;
            while (*p != 0) {
                p[-1]--;
                p--;
            }
            p--;
            while (*p != 0) {
                p += 2;
                while (*p != 0) {
                    p[-2]--;
                    p--;
                }
                p -= 2;
                while (*p != 0) {
                    p--;
                }
            }
            p[1]--;
            p++;
        }
        p[-2] = 0;
        p[-7]++;
        p -= 6;
        while (*p != 0) {
            p[-1]--;
            p--;
        }
        p--;
        if (*p != 0) {
            p += 2;
            while (*p != 0) {
                p[-2]--;
                p--;
            }
            p -= 2;
            while (*p != 0) {
                p--;
            }
        }
		p++;
        p[0]--;
    }
    p -= 2;
    while (*p != 0) {
        putchar(p[0]);
        p--;
    }

    return EXIT_SUCCESS;
}
