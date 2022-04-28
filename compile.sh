# set stopping at first error
set -e

# find all sources files
find src/ -name "*.java" > sources.txt

# echo
echo Compiling $(cat sources.txt | wc -l | tr -d ' ') files...

# compile code with java-compiler
javac \
	--module-path libs:libs/${OS:-linux} \
	--add-modules javafx.controls,javafx.fxml \
	@sources.txt

# creating manifest
rm -rf Manifest.txt
echo "Class-Path:" $(find libs libs/${OS:-linux} -name "*.jar" -maxdepth 1  | tr '\n' ' ') >> Manifest.txt

# compress into a jar file
jar \
	--verbose \
	--create \
	--file app_${OS:-linux}.jar \
	--main-class jeudelavie.Start \
	--manifest Manifest.txt \
	-C src/ .
