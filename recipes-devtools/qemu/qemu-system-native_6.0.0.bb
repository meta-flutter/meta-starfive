BPN = "qemu"

inherit python3-dir

require qemu-native.inc

# As some of the files installed by qemu-native and qemu-system-native
# are the same, we depend on qemu-native to get the full installation set
# and avoid file clashes
DEPENDS = "glib-2.0-native zlib-native pixman-native qemu-native bison-native meson-native ninja-native"

EXTRA_OECONF:append = " --target-list=${@get_qemu_system_target_list(d)}"

PACKAGECONFIG ??= "fdt alsa kvm pie \
    ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'virglrenderer glx', '', d)} \
"

# Handle distros such as CentOS 5 32-bit that do not have kvm support
PACKAGECONFIG:remove = "${@'kvm' if not os.path.exists('/usr/include/linux/kvm.h') else ''}"

do_install:append() {
    install -Dm 0755 ${WORKDIR}/powerpc_rom.bin ${D}${datadir}/qemu

    # The following is also installed by qemu-native
    rm -f ${D}${datadir}/qemu/trace-events-all
    rm -rf ${D}${datadir}/qemu/keymaps
    rm -rf ${D}${datadir}/icons/

    # Install qmp.py to be used with testimage
    install -D ${S}/python/qemu/qmp.py ${D}${PYTHON_SITEPACKAGES_DIR}/qmp.py
}

FILESEXTRAPATHS:prepend := "${THISDIR}/qemu:"

SRC_URI += "file://0001-softfloat-add-APIs-to-handle-alternative-sNaN-propag.patch \
           file://0002-target-riscv-change-the-api-for-single-double-fmin-f.patch \
           file://0003-target-riscv-support-x-Zfh-in-cpu-option.patch \
           file://0004-target-riscv-Implement-zfh-extension.patch \
           file://0005-target-riscv-fix-TB_FLAGS-bits-overlapping-bug-for-r.patch \
           file://0006-fpu-softfloat-set-invalid-excp-flag-for-RISC-V-mulad.patch \
           file://0007-target-riscv-Fixup-saturate-subtract-function.patch \
           file://0008-target-riscv-fix-vrgather-macro-index-variable-type-.patch \
           file://0009-target-riscv-drop-vector-0.7.1-and-add-1.0-support.patch \
           file://0010-target-riscv-Use-FIELD_EX32-to-extract-wd-field.patch \
           file://0011-target-riscv-rvv-1.0-add-mstatus-VS-field.patch \
           file://0012-target-riscv-rvv-1.0-add-sstatus-VS-field.patch \
           file://0013-target-riscv-rvv-1.0-introduce-writable-misa.v-field.patch \
           file://0014-target-riscv-rvv-1.0-add-translation-time-vector-con.patch \
           file://0015-target-riscv-rvv-1.0-remove-rvv-related-codes-from-f.patch \
           file://0016-target-riscv-rvv-1.0-add-vcsr-register.patch \
           file://0017-target-riscv-rvv-1.0-add-vlenb-register.patch \
           file://0018-target-riscv-rvv-1.0-check-MSTATUS_VS-when-accessing.patch \
           file://0019-target-riscv-rvv-1.0-remove-MLEN-calculations.patch \
           file://0020-target-riscv-rvv-1.0-add-fractional-LMUL.patch \
           file://0021-target-riscv-rvv-1.0-add-VMA-and-VTA.patch \
           file://0022-target-riscv-rvv-1.0-update-check-functions.patch \
           file://0023-target-riscv-introduce-more-imm-value-modes-in-trans.patch \
           file://0024-target-riscv-rvv-1.0-add-translation-time-nan-box-he.patch \
           file://0025-target-riscv-rvv-1.0-configure-instructions.patch \
           file://0026-target-riscv-rvv-1.0-stride-load-and-store-instructi.patch \
           file://0027-target-riscv-rvv-1.0-index-load-and-store-instructio.patch \
           file://0028-target-riscv-rvv-1.0-fix-address-index-overflow-bug-.patch \
           file://0029-target-riscv-rvv-1.0-fault-only-first-unit-stride-lo.patch \
           file://0030-target-riscv-rvv-1.0-amo-operations.patch \
           file://0031-target-riscv-rvv-1.0-load-store-whole-register-instr.patch \
           file://0032-target-riscv-rvv-1.0-update-vext_max_elems-for-load-.patch \
           file://0033-target-riscv-rvv-1.0-take-fractional-LMUL-into-vecto.patch \
           file://0034-target-riscv-rvv-1.0-floating-point-square-root-inst.patch \
           file://0035-target-riscv-rvv-1.0-floating-point-classify-instruc.patch \
           file://0036-target-riscv-rvv-1.0-mask-population-count-instructi.patch \
           file://0037-target-riscv-rvv-1.0-find-first-set-mask-bit-instruc.patch \
           file://0038-target-riscv-rvv-1.0-set-X-first-mask-bit-instructio.patch \
           file://0039-target-riscv-rvv-1.0-iota-instruction.patch \
           file://0040-target-riscv-rvv-1.0-element-index-instruction.patch \
           file://0041-target-riscv-rvv-1.0-allow-load-element-with-sign-ex.patch \
           file://0042-target-riscv-rvv-1.0-register-gather-instructions.patch \
           file://0043-target-riscv-rvv-1.0-integer-scalar-move-instruction.patch \
           file://0044-target-riscv-rvv-1.0-floating-point-move-instruction.patch \
           file://0045-target-riscv-rvv-1.0-floating-point-scalar-move-inst.patch \
           file://0046-target-riscv-rvv-1.0-whole-register-move-instruction.patch \
           file://0047-target-riscv-rvv-1.0-integer-extension-instructions.patch \
           file://0048-target-riscv-rvv-1.0-single-width-averaging-add-and-.patch \
           file://0049-target-riscv-rvv-1.0-single-width-bit-shift-instruct.patch \
           file://0050-target-riscv-rvv-1.0-integer-add-with-carry-subtract.patch \
           file://0051-target-riscv-rvv-1.0-narrowing-integer-right-shift-i.patch \
           file://0052-target-riscv-rvv-1.0-widening-integer-multiply-add-i.patch \
           file://0053-target-riscv-rvv-1.0-single-width-saturating-add-and.patch \
           file://0054-target-riscv-rvv-1.0-integer-comparison-instructions.patch \
           file://0055-target-riscv-rvv-1.0-floating-point-compare-instruct.patch \
           file://0056-target-riscv-rvv-1.0-mask-register-logical-instructi.patch \
           file://0057-target-riscv-rvv-1.0-slide-instructions.patch \
           file://0058-target-riscv-rvv-1.0-floating-point-slide-instructio.patch \
           file://0059-target-riscv-rvv-1.0-narrowing-fixed-point-clip-inst.patch \
           file://0060-target-riscv-rvv-1.0-single-width-floating-point-red.patch \
           file://0061-target-riscv-rvv-1.0-widening-floating-point-reducti.patch \
           file://0062-target-riscv-rvv-1.0-single-width-scaling-shift-inst.patch \
           file://0063-target-riscv-rvv-1.0-remove-widening-saturating-scal.patch \
           file://0064-target-riscv-rvv-1.0-remove-vmford.vv-and-vmford.vf.patch \
           file://0065-target-riscv-rvv-1.0-remove-integer-extract-instruct.patch \
           file://0066-target-riscv-rvv-1.0-floating-point-min-max-instruct.patch \
           file://0067-target-riscv-introduce-floating-point-rounding-mode-.patch \
           file://0068-target-riscv-rvv-1.0-floating-point-integer-type-con.patch \
           file://0069-target-riscv-rvv-1.0-widening-floating-point-integer.patch \
           file://0070-target-riscv-add-set-round-to-odd-rounding-mode-help.patch \
           file://0071-target-riscv-rvv-1.0-narrowing-floating-point-intege.patch \
           file://0072-target-riscv-rvv-1.0-relax-RV_VLEN_MAX-to-1024-bits.patch \
           file://0073-target-riscv-rvv-1.0-implement-vstart-CSR.patch \
           file://0074-target-riscv-rvv-1.0-trigger-illegal-instruction-exc.patch \
           file://0075-target-riscv-rvv-1.0-set-mstatus.SD-bit-when-writing.patch \
           file://0076-target-riscv-gdb-support-vector-registers-for-rv64-r.patch \
           file://0077-target-riscv-rvv-1.0-floating-point-reciprocal-squar.patch \
           file://0078-target-riscv-rvv-1.0-floating-point-reciprocal-estim.patch \
           file://0079-target-riscv-set-mstatus.SD-bit-when-writing-fp-CSRs.patch \
           file://0080-target-riscv-rvv-1.0-rename-r2_zimm-to-r2_zimm11.patch \
           file://0081-target-riscv-rvv-1.0-add-vsetivli-instruction.patch \
           file://0082-target-riscv-rvv-1.0-add-evl-parameter-to-vext_ldst_.patch \
           file://0083-target-riscv-rvv-1.0-add-vector-unit-stride-mask-loa.patch \
           file://0084-target-riscv-rvv-1.0-patch-floating-point-reduction-.patch \
           file://0085-target-riscv-reformat-sh-format-encoding-for-B-exten.patch \
           file://0086-target-riscv-rvb-count-leading-trailing-zeros.patch \
           file://0087-target-riscv-rvb-count-bits-set.patch \
           file://0088-target-riscv-rvb-logic-with-negate.patch \
           file://0089-target-riscv-rvb-pack-two-words-into-one-register.patch \
           file://0090-target-riscv-rvb-min-max-instructions.patch \
           file://0091-target-riscv-rvb-sign-extend-instructions.patch \
           file://0092-target-riscv-add-gen_shifti-and-gen_shiftiw-helper-f.patch \
           file://0093-target-riscv-rvb-single-bit-instructions.patch \
           file://0094-target-riscv-rvb-shift-ones.patch \
           file://0095-target-riscv-rvb-rotate-left-right.patch \
           file://0096-target-riscv-rvb-generalized-reverse.patch \
           file://0097-target-riscv-rvb-generalized-or-combine.patch \
           file://0098-target-riscv-rvb-address-calculation.patch \
           file://0099-target-riscv-rvb-add-shift-with-prefix-zero-extend.patch \
           file://0100-target-riscv-rvb-support-and-turn-on-B-extension-fro.patch \
           file://0101-target-riscv-rvb-add-b-ext-version-cpu-option.patch \
           file://0102-target-riscv-fix-REQUIRE_ZFH-macro-bug.patch \
           file://0103-linux-user-elfload-Implement-ELF_HWCAP-for-RISC-V.patch \
           file://0104-target-riscv-Pass-the-same-value-to-oprsz-and-maxsz.patch \
           file://0105-target-riscv-Backup-restore-mstatus.SD-bit-when-virt.patch \
           file://0106-target-riscv-Force-to-set-mstatus_hs.-SD-FS-bits-in-.patch \
           file://0107-target-riscv-Force-to-set-mstatus_hs.-SD-VS-bits-in-.patch \
           file://0001-merge-riscv-bitmapip-b0p94-version.patch \
           file://0002-Add-four-cache-csr-instruction.patch \
           file://0003-Fix-cache-instruction-bug.patch \
           "
