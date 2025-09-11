# Experiment Codes

## 项目描述 (Project Description)

This repository contains a collection of algorithms and utilities implemented in Java. The project is designed to provide solutions for various algorithmic problems, including string matching, maximum subarray problems, and N-Queens problems. It also includes a graphical user interface (GUI) for interacting with the algorithms and a database layer for managing problem data.

本项目来源于中国科学技术大学的程序设计进阶课程大作业，课程号CS400901，本项目得分较高，欢迎参考，如有帮助请点个star，谢谢！该项目包含一系列用Java实现的算法和工具。项目旨在为各种算法问题提供解决方案，包括字符串匹配、最大子数组问题和N皇后问题。项目还包括一个用于与算法交互的图形用户界面（GUI）以及一个用于管理问题数据的数据库层。

---

## 功能 (Features)

- **算法实现 (Algorithm Implementations)**: 提供多种算法的实现，包括：
  - 字符串匹配算法（如KMP、Boyer-Moore）
  - 最大子数组问题（动态规划、枚举）
  - N皇后问题（回溯法、最小冲突法）
- **图形用户界面 (GUI)**: 提供用户友好的界面，用于运行和测试算法。
- **数据库支持 (Database Support)**: 管理算法代码、问题信息和测试用例。
- **模块化设计 (Modular Design)**: 包含多个模块，如服务层、数据访问层、策略模式等。

---

## 目录结构 (Directory Structure)

```
experiment-codes/
├── files/                # 输入文件
├── lib/                  # 配置文件
├── src/                  # 源代码
│   ├── cn/edu/ustc/algorithm/  # 算法实现
│   ├── cn/edu/ustc/app/        # 应用入口
│   ├── cn/edu/ustc/dao/        # 数据访问层
│   ├── cn/edu/ustc/exception/  # 异常处理
│   ├── cn/edu/ustc/gui/        # 图形用户界面
│   ├── cn/edu/ustc/model/      # 数据模型
│   ├── cn/edu/ustc/service/    # 服务层
│   └── cn/edu/ustc/util/       # 工具类
├── uml/                  # UML图
└── README.md             # 项目说明文件
```

---

## 使用说明 (Usage)

1. **运行项目 (Run the Project)**:
   - 使用IDE（如IntelliJ IDEA）打开项目。
   - 配置运行环境并运行`AlgorithmApp`类。

2. **测试算法 (Test Algorithms)**:
   - 在GUI中选择算法类型。
   - 输入测试数据并运行。

3. **扩展功能 (Extend Functionality)**:
   - 在`strategy`包中添加新的算法策略。
   - 在`gui`包中更新界面。

---

## 开发者信息 (Author Information)

- **作者 (Author)**: SiriusPaul
- **版本 (Version)**: 1.0
- **日期 (Date)**: 2025/09/11

---

## 许可证 (License)

This project is licensed under the MIT License. See the LICENSE file for details.

此项目根据MIT许可证授权。有关详细信息，请参阅LICENSE文件。