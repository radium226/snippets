#!/bin/bash

export SUCCESS=0
export FAILURE=1

export OBJCOPY="$( which objcopy )"
export AWK="$( which awk )"
export ECHO="$( which echo )"

export SYMBOL_SUFFIX_START="start"
export SYMBOL_SUFFIX_END="end"
export SYMBOL_SUFFIX_SIZE="size"

file_extension() {
	declare fp="${1}"
	"${ECHO}" "${fp}" | "${AWK}" -F "." '{printf("%s\n", $NF)}'
	return ${SUCCESS}
}

objcopy::symbol_prefix() {
	declare fp="${1}"
	declare prefix="_binary_$( "${ECHO}" "${fp}" | tr "." "_" | tr "/" "_" )"
	echo "${prefix}"
	return ${SUCCESS}
}

snippet::prefix()
{
	declare fp="${1}"
	basename "${fp}" ".$( file_extension "${fp}" )"| awk '{printf("%s\n", tolower($0))}'
	return ${SUCCESS}
}

snippet::objcopy::redefine_symbols()
{
	declare fp="${1}"
	declare objcopy_prefix="$( objcopy::symbol_prefix "${fp}" )"
	declare snippet_prefix="$( snippet::prefix "${fp}" )"
	echo \
		--redefine-sym "${objcopy_prefix}_${SYMBOL_SUFFIX_START}=${snippet_prefix}_${SYMBOL_SUFFIX_START}" \
		--redefine-sym "${objcopy_prefix}_${SYMBOL_SUFFIX_END}=${snippet_prefix}_${SYMBOL_SUFFIX_END}" \
		--redefine-sym "${objcopy_prefix}_${SYMBOL_SUFFIX_SIZE}=${snippet_prefix}_${SYMBOL_SUFFIX_SIZE}" 
	return ${SUCCESS}

}

snippet::objcopy::rename_section()
{
	declare fp="${1}"
	echo \
		--rename-section ".data=.$( snippet::prefix "${fp}" )"
	return ${SUCCESS}
}

embed_binary_file()
{
	declare binary_fp="${1}"
	
	declare linkable_fp="$( dirname "${binary_fp}" )/$( basename "${binary_fp}" ".$( file_extension "${binary_fp}" )" ).o"

	"${OBJCOPY}" \
		--input-target "binary" \
		--output-target "elf64-x86-64" \
		--binary-architecture "i386" \
		$( snippet::objcopy::redefine_symbols "${binary_fp}" ) \
		$( snippet::objcopy::rename_section "${binary_fp}" ) \
		"${binary_fp}" \
		"${linkable_fp}"
	return ${SUCCESS}
}

echo -en "Hello" >"./hello.bin"
echo -en "World" >"./world.bin"

embed_binary_file "./hello.bin"
embed_binary_file "./world.bin"

ld --relocatable "./hello.o" "world.o" --output "./hello_world.o"

gcc "./snippet.c" "./hello_world.o" --output "./snippet"
"./snippet"

