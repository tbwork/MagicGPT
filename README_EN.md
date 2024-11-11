# MagicGPT  

[查看中文文档](/README.md)

[![](https://jitpack.io/v/tbwork/MagicGPT.svg)](https://jitpack.io/#tbwork/MagicGPT)  [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

MagicGPT: Harnessing Local Methods for Task Completion. You can utilize MagicGPT to easily accomplish tasks using local methods:

1.	Clean data from large volumes of unstructured text.
2.	Serve as a 24/7 labeler and auditor.
3.	Act as a 24/7 post administrator.
4.	Replace your company’s backend employees (those relying on self-developed backend systems to complete tasks).
5.	Explore more possibilities waiting to be realized.

## Motivation

In March 2023, the release of ChatGPT based on GPT-3.5 by OpenAI ignited the fuse of the AI era. I eagerly anticipate the maturity of the AI age, where productivity reaches unprecedented heights, allowing everyone ample time to pursue their interests and explore the meaning of life. Before that, open frameworks for various programming languages should be provided for engineers to gradually utilize AI to optimize specific scenarios.

This framework is designed for Java programmers, but developers from other languages are also welcome to discuss MagicGPT solutions for different languages. If you’re interested, you can contact us through our QQ group.

## Current supported features

| Feature             | Supported | Version  |
|---------------------|---------|----------|
| Local methods       | YES     | \>=1.0.0 |

> All other calls can be transformed into local methods; thus, the new version of MagicGPT will only offer local methods. More access forms may be provided in the future, but they will ultimately transition into local methods. Other network calls, database access, and vector database queries can be encapsulated as local methods.

## The mechanism of the MagicGPT

To help everyone better understand the design concept of MagicGPT, we have adopted the popular and easy-to-understand mechanism of the magical world, as shown in the following diagram:

<img src="image/principle.jpg" alt="img" width="80%"> 

You can find corresponding class names in the code and understand their meanings at a glance, just as depicted in all magic worlds:

This should assist you in quickly understanding the relationships and functions of each class. When using it, please note:

1.	The “Chat Wizard” refers to the GPT AI virtual robot that has learned spells.
2.	Each wizard essentially binds a series of spells, and different wizards may bind different spells.
3.	Every wizard can supplement a new AI response based on a conversation context.
4.	When you request a response generation from a wizard based on a dialogue context, you need to specify an output stream.

> Currently, MagicGPT only provides streaming responses, because synchronous responses are too slow.



## How to use

The following sample code demonstrates a basic usage process. Runnable code are in [TestTimeReporter.java](src/test/java/cn/lanehub/ai/examples/timeReporter/TestTimeReporter.java)

```java

    // Create helper class
    MagicGPT magicGPT = new MagicGPT(...);

    // Start a chat
    MagicChat magicChat = magicGPT.startChat(...);

    // Specify output stream and advance the conversation
    magicGPT.proceedChatWithUserMessage(magicChat, "Your message", OutputStream);

```

> Preparation: Make sure that the anole-loader local configuration management framework has been started in the program. For specific usage, please refer to [anole-loader](https://github.com/tbwork/anole-config); This is a foolproof local configuration management framework that can access almost any location of KV configuration without worrying about where the definition file is.

### Dependency configuration

If you haven't configured the JitPack repository, you need to add the following to your project's pom.xml:

```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>

```
> You can also configure it in .m2/settings.xml.


Then import the MagicGPT package:

```xml

    <dependency>
        <groupId>com.github.tbwork</groupId>
        <artifactId>MagicGPT</artifactId>
        <version>${version}</version>
    </dependency>

```

For other package management methods such as Gradle, SBT, Leiningen, please refer to: https://jitpack.io/#tbwork/MagicGPT


### Setting Key Variables

#### GPT3/4 Large Model

Configure the AI_API_KEY in the system environment. Below are the methods for setting environment variables on different operating systems:

Windows

```
1. Open the "Control Panel" and select "System and Security" > "System" > "Advanced system settings".

2. In the "System Properties" dialog box, select the "Advanced" tab, and then click the "Environment Variables" button under "Environment Variables".

3. In the "Environment Variables" dialog box, you can add, edit, and delete user variables and system variables.

4. To add a new system variable, select the "New" button, enter the variable name and value, and then click "OK".
```

MacOS
```
1. Open the "Terminal" application in macOS.

2. Enter the command: `nano ~/.bash_profile`, and then press Enter.

3. In the text editor, you can add, edit, and delete environment variables.

4. After adding the variables, press Control + O to save, and then press Control + X to exit.
```

Linux
```
1. Open the terminal application in Linux.

2. Enter the command: `nano ~/.bashrc`, and then press Enter.

3. In the text editor, you can add, edit, and delete environment variables.

4. After adding the variables, press Control + O to save, and then press Control + X to exit.
```


Alternatively, it can also be defined in any .anole or .properties file (although this method is not recommended as it may lead to privacy leaks).

### Start a chat
```java

    // Specify package name to search for local Call type spells
    MagicGPT magicGPT = new MagicGPT(TestTimeReporter.class.getPackage().getName(),
            OpenAIModel.GPT4_O4_MINI,
            true
    ); 
    // Start a chat
    MagicChat magicChat = magicGPT.startChat(CustomPrompt.buildHeadPrompt(headCustomPrompt), Language.CHINESE);

```

### Proceed a chat

Output to console:

```java

    // Advance a chat, specifying an output stream for the AI's output
    magicGPT.proceedChatWithUserMessage(magicChat, input, new SystemOutputStream());

```

Output to HttpResponse：
```java

    OutputStream outputStream = response.getEntity().getContent();

    // User inputs a sentence, advancing a chat, specifying HttpResponse output stream
    magicGPT.proceedChatWithUserMessage(input, magicChat, outputStream);

```

For complete runnable code, refer to src/test/java under com.magicvector.ai.examples.

**Running result:**

![Time Announcer](image/example_resul.png)

## How to contribute to the code

1.	Ensure you fully understand the magical world mechanism of MagicGPT.
2.	Contributions of any form are welcome: ISSUE suggestions, Pull Requests, group suggestions, etc.
3.	If you wish to modify code in this repository, please create a relevant ISSUE first before submitting a Pull Request.
4.	Do not expose any private data in the code, as we cannot be responsible for data leaks.

## Donation


## Wechat Group
<img src="image/wechatgroup.jpg" alt="img" width="200px">



## Open Source License

This project follows the [MIT Open Source License](https://opensource.org/licenses/MIT).
