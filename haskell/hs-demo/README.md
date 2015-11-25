## Init Project

		mkdir hs-demo
		cd hs-demo
		cabal init

## Build and Run

		cabal configure
		cabal build
		cabal run

## Build and Run II

		cabal sandbox init
		cabal install -j
		.cabal-sandbox/bin/hs-demo.exe

## Build and Run III

		runghc Setup.hs configure
		runghc Setup.hs build
		dist/build/hs-demo/hs-demo.exe
