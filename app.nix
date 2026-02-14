let 
  pkgs = import <nixpkgs> { config = { allowUnfree = false; }; };
  PROJECT_ROOT = builtins.toString ./.;
in
pkgs.mkShell {
  name = "app-shell";

  buildInputs = [
    pkgs.jdk21
  ];

  LANG = "en_US.UTF-8";
  LC_ALL = "en_US.UTF-8";

  shellHook = ''
    export PROJECT_ROOT=${PROJECT_ROOT}

    export JAVA_HOME=${pkgs.jdk21}
    export PATH=${pkgs.jdk21}/bin:$PATH
  '';
}