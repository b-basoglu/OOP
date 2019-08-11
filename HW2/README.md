#
	Objective

		This assignment aims to familiarize you with the development of multi-threaded applications
		and synchronization using Java. Your task is to implement a simulator for production of
		simple manufacturing materials by simulating the different agents within the scenario using
		different threads. Towards this end, you will be using multithreading and concurrency classes
		that are present in java.
		Keywords— Thread, Semaphore, Lock, Executor, Runnable, Callable

	Problem Definition

		We want to simulate the production of manufactured material from elemental ingots. Specif-
		ically, iron and copper ingots are processed to produce copper wires and iron plates.
		
		The processing is handled by four types of agents whose functions are described as below:
		
		• Smelters produce ingots at a certain rate and have two types: iron, and copper. A
		smelter has a limited capacity to store the ingots it produced. It sleeps when its storage
		gets full and wakes up, when ingots are taken. There exists a maximum number of
		ingots that a smelter can produce. Once a smelter reaches this number, it quits.
		• Constructors produce copper wire or iron plates at a certain rate. A copper con-
		structor uses 3 ingots to produce 1 roll of copper wire. Iron constructors uses 2 ingots
		to produce 1 iron plate. Each constructor has a limited storage capacity for incoming
		ingots. Constructors can work while ingots are being loaded into their storage. Con-
		structors quit if it cannot produce its products for a given duration (due to not receiving
		new ingots).
		• Transporters carry ingots from specific smelters to specific constructors. A transporter
		can carry one ingot at a time. The transporters travels to their target smelter, and can
		load ingots from them if the smelter has ingots in its storage. If the smelter does not have
		any ingots in storage, transporter waits on that smelter until ingot becomes available
		or smelter stops. The transporter then travel to its target constructor and unload the
		ingots to its storage if there is available space in its storage. If the constructor does
		not have space, it should wait on that until space becomes available or the constructor
		quits. Transporters work until their smelter stop producing and has no ingots left on
		its storage or its constructor stops.

		Remaining description is described in THE2.pdf.