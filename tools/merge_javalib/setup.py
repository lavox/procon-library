from setuptools import setup

setup(
  name="merge_javalib",
  version="0.1.3",
  py_modules=["merge_javalib"],
  install_requires=[
      "Javalang",
      "pick",
  ],
  entry_points={
    "console_scripts": [
      "merge_javalib = merge_javalib:main",
    ],
  },
)
