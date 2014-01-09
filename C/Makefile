MAKEFLAGS += --no-print-directory
SNIPPETS = $(shell find . -maxdepth 1 -mindepth 1 -type "d")

all: run

run: 
	echo $(shell pwd)
	@for snippet in $(SNIPPETS) ; do \
		if [ -f "$${snippet}/Makefile" ]; then \
			echo " -={ $$( basename $${snippet} ) }=-" ; \
			cd $$snippet ; \
			$(MAKE) $@ ; \
			echo ; \
			cd .. ; \
		fi ; \
	done

