# Packaging icons

Place the Windows installer icon at:

```text
packaging/icons/mIcon256.ico
```

The GitHub Actions workflow uses this file when it is present. The current icon
contains a 256 pixel image. A multi-resolution ICO can also contain 16, 32, and
48 pixel variants for sharper rendering at smaller sizes.

The Linux packages continue to use:

```text
src/main/resources/icons/mIcon256.png
```

That PNG is also the application icon loaded by the Java code at runtime.
