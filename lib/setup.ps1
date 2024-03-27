# Copyright (c) 2023 Sri Lakshmi Kanthan P
#
# This software is released under the MIT License.
# https:#opensource.org/licenses/MIT

# Configure and build the native module
node-gyp clean configure build --target=22.0.2 --arch=x64 --dist-url=https://electronjs.org/headers
