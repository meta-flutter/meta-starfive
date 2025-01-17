LICENSE = "GPLv2 & GPLv3 & LGPLv2 & LGPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552 \
		    file://COPYING3;md5=d32239bcb673463ab874e80d47fae504 \
		    file://COPYING3.LIB;md5=6a6a8e020838b23406c81b19c1d46df6 \
		    file://COPYING.LIB;md5=9f604d8a4f8e74f4f5140845a21b6674"

SRCREV = "5da071ef0965b8054310d8dde9975037b0467311"
BRANCH = "fsf-gdb-10.1-with-sim"

S = "${WORKDIR}/git"

SRC_URI = "git://github.com/riscv/riscv-binutils-gdb.git;branch=${BRANCH};protocol=https \
           file://0001-add-b-v-extension-support.patch \
           file://0002-RISC-V-Hypervisor-ext-drop-Privileged-Spec-1.9.1-and.patch \
           file://0003-RISC-V-Hypervisor-ext-support-Privileged-Spec-1.12.patch \
           file://0004-add-pref-instruction.patch \
           file://0005-Rename-some-assembler-mnemonic-for-rvv1.0.patch \
           file://0001-make-man-install-relative-to-DESTDIR.patch \
           file://0002-mips-linux-nat-Define-_ABIO32-if-not-defined.patch \
           file://0003-ppc-ptrace-Define-pt_regs-uapi_pt_regs-on-GLIBC-syst.patch \
           file://0004-Add-support-for-Renesas-SH-sh4-architecture.patch \
           file://0005-Dont-disable-libreadline.a-when-using-disable-static.patch \
           file://0006-use-asm-sgidefs.h.patch \
           file://0008-Change-order-of-CFLAGS.patch \
           file://0009-resolve-restrict-keyword-conflict.patch \
           file://0010-Fix-invalid-sigprocmask-call.patch \
           file://0011-gdbserver-ctrl-c-handling.patch \
	   file://0001-Automatic-date-update-in-version.in.patch \
	   file://0002-Automatic-date-update-in-version.in.patch \
	   file://0003-Automatic-date-update-in-version.in.patch \
	   file://0004-add-intial-rvv-support-to-display-rvv-registers.patch \
	   file://0005-fix-incorrect-short-size.patch \
	   file://0006-add-Custom-CSR-instructions.patch \
	   file://0007-add-customer-pref-insn-support.patch \
           "
SRC_URI[sha256sum] = "f82f1eceeec14a3afa2de8d9b0d3c91d5a3820e23e0a01bbb70ef9f0276b62c0"
