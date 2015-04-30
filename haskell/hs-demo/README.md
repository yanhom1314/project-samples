## Init Project

		mkdir hs-demo
		cd hs-demo
		cabal init

## Build and Run I

		cabal sandbox init
		cabal install -j
		.cabal-sandbox/bin/hs-demo.exe

## Build and Run II

		runghc Setup.hs configure
		runghc Setup.hs build
		dist/build/hs-demo/hs-demo.exe


		
