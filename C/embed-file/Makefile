OBJCOPY=objcopy
GCC=gcc
LD=ld
MKDIR=mkdir -p
RM=rm
RMDIR=rmdir

ELFDIR=build

TARGET=elf64-x86-64

PROGRAM=snippet

.PHONY: clean

$(ELFDIR)/hello.elf: | $(ELFDIR)
	$(OBJCOPY) \
		--input-target binary \
		--output-target $(TARGET) \
		--binary-architecture i386 \
		--redefine-sym _binary_hello_bin_start=hello_start \
		--redefine-sym _binary_hello_bin_end=hello_end \
		--redefine-sym _binary_hello_bin_size=hello_size \
		--rename-section .data=.hello \
			hello.bin $(ELFDIR)/hello.elf
	
$(ELFDIR)/world.elf: | $(ELFDIR)
	$(OBJCOPY) \
		--input-target binary \
		--output-target $(TARGET) \
		--binary-architecture i386 \
		--redefine-sym _binary_world_bin_start=world_start \
		--redefine-sym _binary_world_bin_end=world_end \
		--redefine-sym _binary_world_bin_size=world_size \
		--rename-section .data=.world \
			world.bin $(ELFDIR)/world.elf

$(ELFDIR)/hello_world.elf: $(ELFDIR)/hello.elf $(ELFDIR)/world.elf | $(ELFDIR)
	$(LD) --relocatable $(ELFDIR)/hello.elf $(ELFDIR)/world.elf --output $(ELFDIR)/hello_world.elf

$(ELFDIR)/$(PROGRAM): $(ELFDIR)/snippet.elf $(ELFDIR)/hello_world.elf | $(ELFDIR)
	$(GCC) -o $@ $^

$(ELFDIR)/%.elf: %.c | $(ELFDIR)
	$(GCC) -c -o $@ $^
	
$(ELFDIR):
	$(MKDIR) -p $@

compile: $(ELFDIR)/$(PROGRAM)

run: compile
	$(ELFDIR)/$(PROGRAM)

clean: | $(ELFDIR)
	$(RM) -R $(ELFDIR)
